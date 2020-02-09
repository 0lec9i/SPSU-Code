using System.Collections.Generic;
using AutomataConversion.Converting;
using AutomataConversion.Converting.LBA;
using JetBrains.Annotations;

namespace GrammarExecution
{
    public interface ITracker
    {
        void Track([NotNull, ItemNotNull] IList<Symbol> track, [NotNull] GrammarRule1 rule);
    }
}
