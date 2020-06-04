import socket
import struct
import time
import datetime

NTP_SERVER = '0.ru.pool.ntp.org'
TIME_DIFF = 2208988800  # (1st January 1970 - 1st January 1900)
PORT = 123


def get_time(n):
    local_shifts = []
    delays = []
    server_time = 0
    for _ in range(n):
        # create the socket, where AF_INET == ipv4 and SOCK_DGRAM == UDP
        client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        # represents a data field of 48 bytes (the size of an NTP UDP packet)
        # 0x1B followed by 47 times 0, where (0x1B) specifies LI, VN, and Mode
        data = '\x1b' + 47 * '\0'
        originate_timestamp = time.time()
        client.sendto(data.encode('utf-8'), (NTP_SERVER, PORT))
        # reads 1024 bytes from a socket returns message and the client address
        data, _ = client.recvfrom(1024)
        arrive_timestamp = time.time()
        if data:
            unpacked_data = struct.unpack('!12I', data)
            # Time of receiving of packet on server (8 bytes)
            receive_timestamp = float('{integral}.{fractional}'.format(integral=unpacked_data[8],
                                                                       fractional=unpacked_data[9])) - TIME_DIFF
            # Time of sending answer from server (8 bytes)
            transmit_timestamp = float('{integral}.{fractional}'.format(integral=unpacked_data[10],
                                                                        fractional=unpacked_data[11])) - TIME_DIFF
            # Packet travel time from client to server
            local_shift = ((arrive_timestamp - originate_timestamp) - (transmit_timestamp - receive_timestamp)) / 2
            delay = receive_timestamp - originate_timestamp - local_shift
            local_shifts.append(local_shift)
            delays.append(delay)
            server_time = transmit_timestamp
    average_shift = sum(local_shifts) / len(local_shifts)
    average_delay = sum(delays) / len(delays)
    current_time = server_time + average_shift
    print('Server time: ', datetime.datetime.fromtimestamp(server_time))
    print('Adjust time: ', datetime.datetime.fromtimestamp(current_time))
    print('Average delay: ', average_delay)


if __name__ == '__main__':
    get_time(3)
