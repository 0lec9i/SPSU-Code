using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using AutomataConversion.Converting;
using AutomataConversion.Converting.LBA;
using AutomataConversion.Parsing;
using AutomataConversion.Resources;
using GrammarExecution;
using JetBrains.Annotations;
using Newtonsoft.Json;

namespace GrammarGeneration
{
    internal static class Program
    {
        public static List<GrammarRule1> FindNotUsed(GrammarType1 grammar)
        {
            Dictionary<GrammarRule1, bool> used = new Dictionary<GrammarRule1, bool>();

            int target = 0;
            string serialized = new ResourceLoader(ResourceNameBundle.UnrestrictedGrammar).Load();

            foreach (var rule in grammar.Rules)
            {
                used.Add(rule, false);
            }

            for (int i = 2; i < 20; i++)
            {
                target = i;
                var tape = new List<Symbol> {grammar.Axiom};
                var tracker = new TrackDerivationTracker(tape);

                GrammarExecution.Program.TryDerive(tape, grammar, tracker, target, used);
            }

            int count = 0;
            List<GrammarRule1> unUsedRules = new List<GrammarRule1>();
            foreach (var key in used.Keys)
            {
                if (!used[key])
                {
                    unUsedRules.Add(key);
                    count++;
                }
            }
            return unUsedRules;
        }


        private static void Main([NotNull, ItemNotNull] string[] args)
        {
            if (args.Length != 1)
            {
                Console.WriteLine("Usage: generator <path-to-output-file>");
                return;
            }

            var loader = new ResourceLoader(ResourceNameBundle.LBADescription);
            string rawTransitions = loader.Load();
            var transitions = TransitionParser.ParseTransitions(rawTransitions);
            var machine = new LBAMachine(transitions.ToList());
            var grammar = machine.Convert();
            foreach (var notUsed in FindNotUsed(grammar))
            {
                if (notUsed.ToString().Equals("A1->1"))
                {
                    continue;
                }

                grammar.Rules.Remove(notUsed);
            }

            string serialized = JsonConvert.SerializeObject(grammar);
            File.WriteAllText(args.Single(), serialized);
        }
    }
}
