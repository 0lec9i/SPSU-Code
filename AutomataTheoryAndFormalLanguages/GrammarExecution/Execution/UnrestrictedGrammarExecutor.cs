using System.Collections.Generic;
using System.Linq;
using AutomataConversion.Converting;
using AutomataConversion.Converting.TurinMachine;
using GrammarExecution;
using JetBrains.Annotations;

namespace AutomataConversion.Execution
{
    public static class UnrestrictedGrammarExecutor
    {
        public static void Derive([NotNull, ItemNotNull] this List<Symbol> tape, [NotNull] GrammarType0 grammar,
            [NotNull] IDerivationTracker tracker)
        {
            while (tape.TryDerive(grammar, tracker))
            {
            }
        }

        private static bool TryDerive([NotNull, ItemNotNull] this List<Symbol> tape, [NotNull] GrammarType0 grammar,
            [NotNull] IDerivationTracker tracker)
        {
            if (!grammar.Rules.Any(rule => TryDerive(tape, rule, tracker))) return false;
            return true;
        }

        public static bool TryDerive([NotNull, ItemNotNull] this List<Symbol> tape, [NotNull] GrammarRule0 rule,
            IDerivationTracker tracker) =>
            tape.Where((t, index) => TryDerive(tape, index, rule, tracker)).Any();

        private static bool TryDerive([NotNull, ItemNotNull] List<Symbol> tape,
            int start,
            [NotNull] GrammarRule0 rule, IDerivationTracker tracker)
        {
            if (tape.Count < start + rule.LeftHandSide.Count) return false;
            var sublist = tape.GetRange(start, rule.LeftHandSide.Count);
            if (!sublist.SequenceEqual(rule.LeftHandSide)) return false;
            tape.RemoveRange(start, rule.LeftHandSide.Count);
            tape.InsertRange(start, rule.RightHandSide);
            tracker.Track(tape, rule);
            return true;
        }
    }
}
