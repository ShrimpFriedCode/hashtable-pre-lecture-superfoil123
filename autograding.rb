#!/usr/bin/env ruby

############
# Requires #
############

require 'yaml'
require 'json'

#############
# Constants #
#############

JAR_FILES = [
	'/usr/share/java/hamcrest/core.jar',
	'/usr/share/java/junit.jar'
]

DEFAULT_CLASSPATH = JAR_FILES.join(':')

JAVAC_OPTS = '-nowarn'

COMPILATION_MAX_SCORE = 10

TEST_RUNNER_CLASS = 'IUAutogradingRunner'

# Load the names of our unit tests

TEST_CASES = (YAML.load_file("ts_handout.yaml") || Hash.new).merge(YAML.load_file("ts_grading.yaml") || Hash.new)

####################
# Helper Functions #
####################

def check_file(file_name)
	if not File.exists?(file_name)
		puts "Unable to locate file: #{file_name}"
		exit
	end
end

def fail_compilation
	fail_score = TEST_CASES.keys.inject(Hash.new) { |h, k| h[k] = 0; h }
	fail_score['Compilation'] = 0

	puts JSON.generate({scores: fail_score})
	exit
end

def check_config
	JAR_FILES.each do |file_name|
		check_file(file_name)
	end
end

def compile(dir_name, more_classpath = '')
	source_files = Dir[File::join(dir_name, '*.java')].join(' ')
	classpath    = "#{DEFAULT_CLASSPATH}:#{dir_name}"

	fail_compilation if source_files.empty?

	if not more_classpath.empty?
		classpath += ':' + more_classpath
	end

	result = `javac -cp #{classpath} #{JAVAC_OPTS} #{source_files} 2>&1`

	if not $?.success?
		puts result
		fail_compilation
	end
end

def run_test(name, max_score, failure_messages)
	classpath = "#{DEFAULT_CLASSPATH}:src/:test/"

	result = `java -cp #{classpath} #{TEST_RUNNER_CLASS} #{name}`

	result_lines = result.lines

	failure_messages << result_lines[0...-1]
	pass_fail_line    = result_lines.last

	total, successes, failures = pass_fail_line.split.map &:to_i

	max_score * (successes.to_f / total)
end

###############
# Script Body #
###############

check_config()

# Compile the src/ directory
compile('src/')

# Compile the test/ directory
compile('test/', 'src/')

grades           = {'Compilation' => COMPILATION_MAX_SCORE}
failure_messages = []

TEST_CASES.each do |name, max_score|
	grades[name] = run_test(name, max_score, failure_messages)
end

failure_messages.flatten.each {|msg| puts msg}
puts
puts JSON.generate({scores: grades})
