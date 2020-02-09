using System.Collections.Generic;
using AutomataConversion.Converting;
using AutomataConversion.Converting.LBA;
using JetBrains.Annotations;

namespace GrammarExecution
{
    public sealed class Rule1UsageTracker : ITracker
    {
        [NotNull, ItemNotNull]
        public ISet<GrammarRule1> Rules { get; } = new HashSet<GrammarRule1>();

        public void Track(IList<Symbol> track, GrammarRule1 rule) => Rules.Add(rule);
    }
}
