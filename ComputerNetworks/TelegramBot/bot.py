import telebot
import random
import utils
from SQLighter import SQLighter
from telebot import types
from telebot import apihelper
from config import token, database_name

apihelper.proxy = {'https': 'socks5://userproxy:password@grsst.s5.opennetwork.cc:999'}
bot = telebot.TeleBot(token)


@bot.message_handler(func=lambda message: True, commands=['start'])
def start(message):
    markup = types.InlineKeyboardMarkup()
    button1 = types.InlineKeyboardButton(text="Начнем?", callback_data="game")
    markup.add(button1)
    bot.send_message(chat_id=message.chat.id, text="Надо указать художника, написавшего предложенную картину", reply_markup=markup)


@bot.callback_query_handler(func=lambda call: True)
def game(call):
    if call.data == "game":
        db_worker = SQLighter(database_name)
        row = db_worker.select_single(random.randint(1, utils.get_rows_count()))
        markup = utils.generate_markup(row[2], row[3])
        img = open("pictures/" + row[1] + '.jpg', 'rb')
        bot.send_photo(call.message.chat.id, img, reply_markup=markup)
        utils.set_user_game(call.message.chat.id, row[2])
        img.close()
        db_worker.close()


@bot.message_handler(func=lambda message: True, content_types=['text'])
def check_answer(message):
    answer = utils.get_answer_for_user(message.chat.id)
    keyboard_hider = types.ReplyKeyboardRemove()
    if message.text == answer:
        bot.send_message(message.chat.id, 'Верно!', reply_markup=keyboard_hider)
        start_keyboard = types.InlineKeyboardMarkup()
        start_service = types.InlineKeyboardButton(text='Еще?)', callback_data='game')
        start_keyboard.add(start_service)
        bot.send_message(message.chat.id, '☺', reply_markup=start_keyboard)
    else:
        bot.send_message(message.chat.id, 'Увы, Вы не угадали.', reply_markup=keyboard_hider)
        start_keyboard = types.InlineKeyboardMarkup()
        start_service = types.InlineKeyboardButton(text='Попытка - не пытка', callback_data='game')
        start_keyboard.add(start_service)
        bot.send_message(message.chat.id, 'Не расстраивайтесь, может еще получится :)', reply_markup=start_keyboard)
    utils.finish_user_game(message.chat.id)


if __name__ == '__main__':
    utils.count_rows()
    random.seed()
    bot.polling(none_stop=True, timeout=123)