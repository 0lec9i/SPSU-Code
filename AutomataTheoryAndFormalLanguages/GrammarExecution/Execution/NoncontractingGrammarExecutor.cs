using AutomataConversion.Converting.LBA;
using System.Collections.Generic;
using System.Linq;
using AutomataConversion.Converting;
using GrammarExecution;
using JetBrains.Annotations;

namespace AutomataConversion.Execution
{

    public static class NoncontractingGrammarExecutor
    {
        public static void Derive([NotNull, ItemNotNull] this List<Symbol> tape, [NotNull] GrammarType1 grammar,
            [NotNull] ITracker tracker, [CanBeNull] Dictionary<GrammarRule1, bool> used)
        {
            while (tape.TryDerive(grammar, tracker, used))
            {
            }
        }

        private static bool TryDerive([NotNull, ItemNotNull] this List<Symbol> tape, [NotNull] GrammarType1 grammar,
            [NotNull] ITracker tracker, [CanBeNull] Dictionary<GrammarRule1, bool> used)
        {
            if (!grammar.Rules.Any(rule => TryDerive(tape, rule, tracker, used))) return false;
            return true;
        }

        public static bool TryDerive([NotNull, ItemNotNull] this List<Symbol> tape, [NotNull] GrammarRule1 rule,
            ITracker tracker, [CanBeNull] Dictionary<GrammarRule1, bool> used) =>
            tape.Where((t, index) => TryDerive(tape, index, rule, tracker, used)).Any();

        private static bool TryDerive([NotNull, ItemNotNull] List<Symbol> tape,
            int start,
            [NotNull] GrammarRule1 rule, ITracker tracker, [CanBeNull] Dictionary<GrammarRule1, bool> used)
        {
            if (tape.Count < start + rule.LeftHandSide.Count) return false;
            var sublist = tape.GetRange(start, rule.LeftHandSide.Count);
            if (!sublist.SequenceEqual(rule.LeftHandSide)) return false;
            if (used != null)
            {
                used[rule] = true;
            }

            tape.RemoveRange(start, rule.LeftHandSide.Count);
            tape.InsertRange(start, rule.RightHandSide);
            tracker.Track(tape, rule);
            return true;
        }
    }

}
