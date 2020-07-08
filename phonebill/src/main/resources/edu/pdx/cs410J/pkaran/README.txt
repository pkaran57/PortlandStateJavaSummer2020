Student name: Karan Patel, Project 1: Designing a Phone Bill Application

This application generates a Phone Bill with a Phone Call and optionally prints information about the Phone Call.

Usage: java edu.pdx.cs410J.pkaran.Project1 [options] <args>
	args are (in this order):
		customer - Person whose phone bill we’re modeling
		callerNumber - Phone number of caller
		calleeNumber - Phone number of person who was called
		start - Date and time call began (24-hour time)
		end - Date and time call ended (24-hour time)
	options are (options may appear in any order):
		-print Prints a description of the new phone call
		-README Prints a README for this project and exits
Date and time should be in the format: mm/dd/yyyy hh:mm