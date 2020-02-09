using System.Collections.Generic;
using System.Text;
using AutomataConversion.Converting;
using AutomataConversion.Converting.LBA;
using JetBrains.Annotations;

namespace GrammarExecution
{
    public sealed class TrackDerivationTracker : ITracker
    {
        public TrackDerivationTracker([NotNull, ItemNotNull] IList<Symbol> track) =>
            Builder.AppendLine(string.Join("", track));

        [NotNull]
        private StringBuilder Builder { get; } = new StringBuilder();

        public void Track(IList<Symbol> track, GrammarRule1 rule) => Builder.AppendLine(string.Join("", track));

        [NotNull]
        public string Result => Builder.ToString();
    }
}
