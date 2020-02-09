Automata conversion
---
This code can perform the following actions:  
Convert LBA to grammar type 1 
Check whether a string matches that grammar

---
Building project:

First, you need .NET Core SDK.
Installation instructions: https://dotnet.microsoft.com/download

Installation instructions for Windows can be found at https://dotnet.microsoft.com/download/thank-you/dotnet-sdk-3.0.100-windows-x64-installer.  

The recommended way of building project is to open it in Rider and select a run configuration.  
It is, however, also possible to run it from the command line. To do that, run
```bash
cd AutomataConversion\GrammarGeneration
dotnet run Foo.json
cd ..\GrammarExecution
dotnet run 5
```
---
Format description and machine description can be found in `AutomataConversion\Resources` folder

The generated grammar will appear at `GrammarGeneration\Foo.json`

The optimized grammar type 1 is at `AutomataConversion\Resources\CSGGrammar.txt`