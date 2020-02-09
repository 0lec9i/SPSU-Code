using System;
using System.Collections.Generic;
using System.Linq;
using AutomataConversion.Converting;
using AutomataConversion.Converting.LBA;
using AutomataConversion.Execution;
using AutomataConversion.Resources;
using JetBrains.Annotations;
using Newtonsoft.Json;

namespace GrammarExecution
{


    public static class Program
    {

        private static void Main([NotNull, ItemNotNull] string[] args)
        {
            if (args.Length != 1 || !int.TryParse(args.Single(), out int target))
            {
                Console.WriteLine("Usage: executor <number>");
                return;
            }

            string serialized = new ResourceLoader(ResourceNameBundle.CSGGrammar).Load();
            var grammar = JsonConvert.DeserializeObject<GrammarType1>(serialized);
            var tape = new List<Symbol> {grammar.Axiom};
            var tracker = new TrackDerivationTracker(tape);

            TryDerive(tape, grammar, tracker, target, null);
            if (string.Join("", tape).Length != target)
            {
                Console.WriteLine("That number is not prime.");
                return;
            }

            Console.WriteLine("That number is prime!");
            Console.WriteLine("Derivation:");
            Console.WriteLine(tracker.Result);
        }

        public static void TryDerive(
            [NotNull, ItemNotNull] List<Symbol> tape,
            [NotNull] GrammarType1 grammar,
            [NotNull] ITracker tracker,
            int target,
            [CanBeNull] Dictionary<GrammarRule1, bool> used
        )
        {

            if (target == 1)
            {
                tracker.Track(new List<Symbol> {new Symbol("1")}, null);
                tape[0] = new Symbol("1");
                return;
            }

            tape.TryDerive(grammar.Rules[1], tracker, used);
            for (int i = 0; i < target - 2; i++)
            {
                tape.TryDerive(grammar.Rules[2], tracker, used);
            }

            tape.TryDerive(grammar.Rules[3], tracker, used);
            tape.Derive(grammar, tracker, used);
        }
    }
}
