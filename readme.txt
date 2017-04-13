Assumptions for shell application:
	-Pattern: each pattern is deemed as invalid if the method Pattern.compile(pattern) throw PatternSyntaxException
	-Application's argument order:
		+ Argument must be in the order [App name] " " [Option(s)] " " [File(s)].
		+ Argument must start with application name.
		+ Options can be in any order (if any).
		+ Option(s) must come before file(s) (if any).
	-WcApplication: 
		+ If no options is given, the order of output is byteCount wordCount lineCount fileName.
		+ If n files are given, there are n output lines, each of them gives the count for 1 file and ends with that file name.
		+ For the options given, the output will be printed in the same order as the options. Ex: options = {"-w -m"}, then the output is wordCount byteCount.
		+ If there are repeated options, eg {"-m -m"}, they are still treated as different options, ie output is byteCount byteCount.
		+ There are no composite options, eg -mw, each options have to be given as "-" and either "m", "w", or "l"
	-CalApplication:
		+ Year must be >= 0.
		+ Order of arugments ar {-m} {month} {year} ( -m can not appear after month or year, otherwise an error will be thrown.
	-Sort:
		+ All lines are sorted by ASCII values
		+ "+ and -" before numbers are treated as numerical signs
		+ -n option is only acceptable as first argument, it can not be provided after file paths
		
