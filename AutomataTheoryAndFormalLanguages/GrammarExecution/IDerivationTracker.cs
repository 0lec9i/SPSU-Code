using System.Collections.Generic;
using AutomataConversion.Converting;
using AutomataConversion.Converting.TurinMachine;
using JetBrains.Annotations;

namespace GrammarExecution
{
    public interface IDerivationTracker
    {
        void Track([NotNull, ItemNotNull] IList<Symbol> track, [NotNull] GrammarRule0 rule);
    }
}
