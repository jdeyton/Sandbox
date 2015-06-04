#!/usr/local/bin/python2.7
# encoding: utf-8
'''
com.bar.foo.pyground.LicenseFixer -- Attempts to re-generate a file's license.

This script/class can be used to generate a license for a given file. It can 
take as input files containing a license format, a list of author names, and a 
list of default contributors. Metadata about the file's provenance is obtained 
from both the git history of the file and the file's own existing documentation.
This metadata is combined with the input license/author/contributor files to 
generate a default license for the source file. For more information, see the 
class documentation for LicenseFixer.

This script also includes a main method that takes paths to source files and 
optional arguments to define the license, authors, and contributors files.

@author:     Jordan H. Deyton
'''

import sys

# Used to check path validity.
import os.path
# Import the logging utility.
import logging as log
# Import the regex library.
import re
# Used for running git commands and getting their output.
import subprocess

from argparse import ArgumentParser
from argparse import RawDescriptionHelpFormatter
from datetime import datetime

__all__ = []
__version__ = 0.1
__date__ = '2015-05-28'
__updated__ = '2015-05-28'

DEBUG = 1
TESTRUN = 0
PROFILE = 0

class LicenseFixer():
    '''
    This class provides the functionality required to generate licenses for the 
    specified source files. Callers should use the method fixLicense(path) to 
    update the license for a particular file.
    '''
    # ---- global configuration ---- #
    # These should only be set once.
    '''
    The license format string read in from the file. It should include the date,
    copyright owner(s), initial author, and contributor tags for fast 
    replacement when generating a license for a file.
    '''
    _licenseFormat = None
    '''
    The author dictionary maps names that commonly appear in the existing 
    documentation or in the git history to preferred user names.
    '''
    _authorDictionary = None
    '''
    The employer dictionary maps author names to their employers. Employer names
    should be appended in parentheses after the author's name in the license 
    text.
    '''
    _employerDictionary = None
    '''
    The default contributor set provides a list of contributors for a given 
    employer. All contributors should be listed in cases where the history of 
    the file pre-dates the git repo and has no history since the git repo's 
    inception.
    '''
    _defaultContributorSet = None
    '''
    The string of copyright owners to apply to each license.
    '''
    _defaultCopyrightOwners = None
    
    # TODO Add the cutoff date of Nov. 4, 2014. That's when most everything was 
    # migrated over.
    # ------------------------------ # 
    
    # ---- git metadata ---- #
    _dateList = None
    _authorSet = None
    _isOld = False
    # ---------------------- #
    
    # ---- existing documentation metadata ---- #
    _existingDateList = None
    _existingAuthorSet = None
    # ----------------------------------------- #
    
    _charLimit = 80
    
    _commentBlocks = None
    
    def __init__(self, licenseFile, authorsFile, contributorsFile):
        '''
        Constructs a new LicenseFixer instance.
        @param licenseFile: The path to a file containing a license format. This
        file should not include any comment characters, but may include the 
        following format strings that will be replaced:
            {DATE} - The start/end years for the license.
            {COPYRIGHT_OWNERS} - All copyright owners, usually in a comma 
            separated list.
            {AUTHOR} - The initial author that committed the work.
            {CONTRIBUTORS} - Any additional contributors go here.
        @param authorsFile: The path to a file containing a row of 
        comma-separated values for common authors. The first value in a row 
        should be the preferred name of an author in the documentation, while 
        subsequent values in the row are common names used elsewhere in the 
        documentation.
        @param contributorsFile: The path to a file containing all "default" 
        contributors to add when the history of the file is unknown past a 
        certain point. Each contributor name should be on a separate line. 
        '''
        # Log the specified input files.
        log.debug('license file: {0}'.format(licenseFile))
        log.debug('authors file: {0}'.format(authorsFile))
        log.debug('contributors file: {0}'.format(contributorsFile))
        
        # Set up the defaults. These shouldn't change.
        self._createLicenseFormat(licenseFile)
        self._createAuthorDictionary(authorsFile)
        self._createContributorSet(contributorsFile)
        self._defaultCopyrightOwners = 'UT-Battelle, LLC.'
        
        return
    
    def _createLicenseFormat(self, licenseFile):
        '''
        Reads in the content from the specified file into a string.
        @param licenseFile: The file from which to read. If not a file or cannot
        be opened, no license format is returned.
        @return: The license text format read from the file, or the empty string
         if the license text file could not be read.
        '''
        licenseFormat = ""
        # If possible, read the license format from the file.
        if os.path.isfile(licenseFile):
            # The with here ensures the file is properly closed afterwards.
            with open(licenseFile, 'r') as lFile:
                for line in lFile:
                    licenseFormat += line
            file.closed
        # Otherwise, post a warning message.
        else:
            log.warn('The specified file "{0}" is not a file or cannot be read.', licenseFile)
        # Post a debug message showing the license format read from the file.
        log.debug('License format: {0}{1}'.format(os.linesep, licenseFormat))
        
        # Set the global value.
        self._licenseFormat = licenseFormat
        
        return licenseFormat
    
    def _createAuthorDictionary(self, authorsFile):
        '''
        Reads in the content from the specified file into a dictionary of 
        authors. Each name will be mapped to its preferred name. Alternate names
        are sepearated by commas after the first (preferred) name. Empty rows in
        the file are ignored.
        @param authorsFile: The file from which to read. If not a file or cannot
        be opened, no preferred names will be stored, and all committers listed 
        in the git history will be listed as contributors.
        @return: A dictionary of author names. The keys will be names that 
        appear in the metadata, while the values will be the preferred name.
        '''
        authorDictionary = {}
        
        authorCount = 0;
        
        # If possible, read the author list from the file.
        if os.path.isfile(authorsFile):
            # The with here ensures the file is properly closed afterwards.
            with open(authorsFile, 'r') as aFile:
                # Each line should be a comma-separated list of names. The 
                # preferred name is the first one. 
                for line in aFile:
                    names = line.split(',')
                    # Get the preferred name. This is the first value in the row
                    # that is a non-empty string after trimming spaces.
                    for name in names:
                        trimmedName = name.strip()
                        if trimmedName:
                            authorCount += 1
                            preferredName = trimmedName
                            # Map each listed, non-empty name to the preferred 
                            # one in the dictionary.
                            for name in names:
                                trimmedName = name.strip();
                                if trimmedName:
                                    # Print out the read name to the debug log.
                                    log.debug('Common name "{0}" mapped to preferred name "{1}".'.format(trimmedName, preferredName))
                                    authorDictionary[trimmedName] = preferredName    
                            break
            file.closed
        # Otherwise, post a warning message.
        else:
            log.warn('The specified file "{0}" is not a file or cannot be read.'.format(authorsFile))
        # Post a debug message showing the number of authors and common names
        # read from the file.
        log.debug('Authors: {0} authors, {1} total names.'.format(authorCount, len(authorDictionary)))
        
        self._authorDictionary = authorDictionary
        self._employerDictionary = {}  # TODO
        
        return authorDictionary
    
    def _createContributorSet(self, contributorsFile):
        '''
        Reads in the list of default contributors from a file. This list will be
        placed after the author line in the license provided the git history is 
        incomplete.
        @param contributorsFile: The file from which to read. If not a file or 
        cannot be opened, no contributors will be listed when the git history is
        incomplete.
        @return: A set of contributor names. May be empty if the file was empty 
        or could not be read.
        '''
        contributorSet = set()
        
        contributorCount = 0;
        
        # If possible, read the contributor list from the file.
        if os.path.isfile(contributorsFile):
            # The with here ensures the file is properly closed afterwards.
            with open(contributorsFile, 'r') as aFile:
                # Each line can represent a list of contributors for a given 
                # employer. Only add non-empty lines to the set.
                for line in aFile:
                    trimmedLine = line.strip()
                    if trimmedLine:
                        contributorSet.add(trimmedLine)
                        contributorCount += len(trimmedLine.split(','))                    
            file.closed
        # Otherwise, post a warning message.
        else:
            log.warn('The specified file "{0}" is not a file or cannot be read.'.format(contributorsFile))
        # Post a debug message showing the number of contributors read from the 
        # file.
        log.debug('Contributors: {0} total representing {1} employers.'.format(contributorCount, len(contributorSet)))
        
        # Set the global value.
        self._defaultContributorSet = contributorSet
        
        return contributorSet
    
    def generateLicense(self, path):
        licenseText = ""
        
        # Check the input path before proceeding.
        if not os.path.isfile(path):
            log.warn('The specified file "{0}" is not a file or cannot be read.', path)
            log.warn('Generating an empty license.')
            return licenseText
        log.debug('Generating license for file:{0}'.format(path))
        
        # The line separator. This is useful in various places in this method.
        sep = '\n'  # os.linesep
        
        # Grab all of the comments from the file.
        self._findComments(path)
        
        # Gather all possible metadata from the file and its git history.
        self._findDocMetadata(path)
        self._findGitMetadata(path)
        
        # Determine the substitutions that need to go in the license text.
        dates = self._getDates()
        copyrightOwners = self._getCopyrightOwners()
        initialAuthor = self._getInitialAuthor()
        contributorList = self._getContributors()
        
        # Convert the contributors into a (perhaps multi-line) string.
        contributorsString = ""
        for contributor in contributorList:
            contributorsString += contributor
            contributorsString += sep
        
        # Replace the format keys in the license format with the determined 
        # values for the file.
        licenseFormat = self._licenseFormat
        pattern = re.compile('{DATE}')
        licenseFormat = pattern.sub(dates, licenseFormat);
        pattern = re.compile('{COPYRIGHT_OWNERS}')
        licenseFormat = pattern.sub(copyrightOwners, licenseFormat);
        pattern = re.compile('{AUTHOR}')
        licenseFormat = pattern.sub(initialAuthor, licenseFormat);
        pattern = re.compile('{CONTRIBUTORS}')
        licenseFormat = pattern.sub(contributorsString, licenseFormat);     
        
        # Build the license text using the appropriate multiline comment
        # characters for the source file.
        starter = self._getMultilineCommentLineStarter(path)
        licenseText = self._getMultilineCommentFirstLine(path) + sep
        for line in licenseFormat.splitlines():
            # Get the next line of the output license text.
            outputLine = starter + line
            # If the line is longer than the character limit, split it over
            # multiple lines. There's also logic here to not break words.
            while len(outputLine) > self._charLimit:
                i = outputLine.rfind(' ', 0, self._charLimit) + 1
                licenseText += outputLine[:i] + sep
                outputLine = starter + outputLine[i:]
            # Add the last (or only) output line.
            licenseText += outputLine + sep
        licenseText += self._getMultilineCommentLastLine()
        
        return licenseText
    
    def _findComments(self, path):
        '''
        Finds all comment blocks and places them (excluding the comment opener, 
        line openers (if present), and the comment ender) as separate strings, 
        one for each block, in self._commentBlocks
        @param path: The path to the source file in question.
        @return: Nothing. _commentBlocks is modified.
        '''
        
        # Opens the file and searches for all content in multiline comments. 
        # Note: This is potentially dangerous as the files contents are read 
        # into memory. Although this is (at least currently) highly unusual for 
        # a source file to be beyond a few thousand lines.
        with open(path, 'r') as f:
            self._commentBlocks = re.findall('/\*+(.*?)\*+/', f.read(), re.DOTALL)    
        file.closed
        
        # Replace all leading asterisks. We shouldn't destroy empty lines, hence
        # the \n is omitted from the second amount of whitespace characters.
        regex = re.compile('^\s*\*+[ \t\r\f\v]*', re.MULTILINE)
        for i in range(len(self._commentBlocks)):
            self._commentBlocks[i] = regex.sub('', self._commentBlocks[i])
        
        return
    
    def _findDocMetadata(self, path):
        '''
        Constructs all metadata that can be obtained from the specified file's
        existing documentation. This includes clearing and updating 
        _existingDateList and _existingAuthorSet.
        @param path: The path to the file whose documentation will be scanned.
        @return: Nothing. _existingDateList and _existingAuthorSet are modified.
        '''
        # Clear out the previous metadata.
        self._existingDateList = []
        self._existingAuthorSet = set()
        
        # If the header comment contains the copyright date info, try to get the
        # first (and last year, if available) from it.
        if len(self._commentBlocks) > 0:
            headerComment = self._commentBlocks[0]
            result = re.match('^.*Copyright.*?\s+(\d{4})(,\s*(\d{4}))?.*$', headerComment, re.MULTILINE)
            if result:
                self._existingDateList.append(int(result.group(1)))
                if len(result.groups()) == 3:
                    self._existingDateList.append(int(result.group(3)))
        
        # Print the found dates to the debug log.
        if len(self._existingDateList) == 0:
            log.debug('Found no existing copyright date.')
        else:
            log.debug('Found existing copyright dates: {0}'.format(self._existingDateList))
        
        # Find the authors for the @author tags.
        regex = re.compile('^author')
        for commentBlock in self._commentBlocks:
            # Split the comment block into sections by @ tags.
            tagSplit = commentBlock.split('@')
            # Process each section after an @ sign where the first string is 
            # 'author' (this is in the pre-compiled regex).
            for i in range(1, len(tagSplit)):
                tagBlock = tagSplit[i]
                result = regex.match(tagBlock)
                # An author tag was found!
                if result:
                    # Ignore the 'author' part of the tag "block".
                    authors = tagBlock.split()
                    authors.pop(0)
                    # Replaces all whitespace with a single space.
                    authors = ' '.join(authors).split(',')
                    # Loops over each found author and either adds their 
                    # preferred name or the trimmed name to the set of existing 
                    # authors.
                    for author in authors:
                        author = author.strip()
                        if author in self._authorDictionary:
                            self._existingAuthorSet.add(self._authorDictionary[author])
                        else:
                            self._existingAuthorSet.add(author)
        
        # Print the found authors to the debug log.
        if len(self._existingAuthorSet) == 0:
            log.debug('Found no existing authors from author tags.')
        else:
            log.debug('Found existing authors from author tags: {0}'.format(self._existingAuthorSet))
        
        return
    
    def _findGitMetadata(self, path):
        '''
        Constructs all metadata that can be obtained from the specified file's
        git history. This includes clearing and updating _dateList, _authorSet, 
        and _isOld.
        @param path: The path to the file whose git history will be queried.
        @return: Nothing. _dateList, _authorSet, and _isOld are modified.
        '''
        # Clear out the previous metadata.
        self._dateList = []
        self._authorSet = set()
        self._isOld = False
        
        # Call git log on the file. We need to pass --pretty=format:"%ci,%an" to
        # get the log output in a simple format: yyyy-mm-dd -gmtdiff,<author>
        directory = path[:path.rfind(os.sep)]
        result = subprocess.check_output(['git', '-C', directory, 'log', '--pretty=format:"%ci,%an"', path], stderr=subprocess.STDOUT)
        
        commits = result.replace('"', '').splitlines()        
        
        # Determine the years for the first and last commits.
        commit = commits[0]
        lastYear = int(commit[:commit.find('-')])
        commit = commits[len(commits) - 1]
        firstYear = int(commit[:commit.find('-')])
        # Update self._dateList to hold the first (and last year if different).
        self._dateList.append(firstYear)
        if firstYear != lastYear:
            self._dateList.append(lastYear)
        
        # Print the found first/last date(s) to the log.
        log.debug('Found the dates from the git history: {0}'.format(self._dateList))
        
        # Determine whether the file is old.
        firstDateString = commit.split()[0].split('-')
        firstMonth = int(firstDateString[1])
        firstDay = int(firstDateString[2])
        if datetime(firstYear, firstMonth, firstDay) < datetime(2014, 11, 4):
            self._isOld = True
        
        # Print out whether or not the file is old to the log.
        if self._isOld:
            log.debug('The file predates the repo relocation.')
        else:
            log.debug('The file is more recent than the repo relocation.')
        
        # Add all authors from the commit log to the set of authors. Use the 
        # preferred name if available.
        authorSet = set()
        for commit in commits:
            authorSet.add(commit.split(',')[1])
        for author in authorSet:
            if author in self._authorDictionary:
                self._authorSet.add(self._authorDictionary[author])
            else:
                self._authorSet.add(author)
        
        # Print out the added authors to the log.
        log.debug('Found authors from the git history: {0}'.format(self._authorSet))
        
        return
    
    def _getDates(self):
        '''
        Uses the current metadata for the file to determine the proper date 
        string to use for the file's license. If the dates span multiple years,
        the returned string will be of the format "first_year, last_year". 
        Otherwise, the returned string will be of the format "first_year".
        
        This method should not return a null value.
        '''
        dates = ""
        # TODO
        dates = "2001 a space odyssey"
        return dates
    
    def _getCopyrightOwners(self):
        '''
        Determines the copyright owner string to use for the file's license. 
        
        This method should not return a null value.
        '''
        copyrightOwners = self._defaultCopyrightOwners
        return copyrightOwners
    
    def _getInitialAuthor(self):
        '''
        Uses the current metadata for the file to determine the proper initial
        author string to use for the file's license. If the file pre-dates the
        move to the git repo, then the author provided by the class author tag
        will be used. If the file has no such author specified, the default 
        author will be used.
        
        This method should not return a null value.
        '''
        author = ""
        # TODO
        author = "PRIMECUTMIGGITYMOEMACKDADDYJIZZYBANGDOGGYDOGDAWG"
        return author
    
    def _getContributors(self):
        '''
        Uses the current metadata for the file to determine the proper list of
        contributors to use for the file's license. If the file pre-dates the
        move to the git repo AND has no history after the move, then the default
        list of contributors will be used.
        
        This method returns a *list*. It method should not return a null value, 
        but may return an empty list.
        '''
        contributors = []
        # TODO
        for contributor in self._defaultContributorSet:
            contributors.append(contributor)
        return contributors
    
    # TODO Make these better.
    def _getMultilineCommentFirstLine(self, path):
        return "/*******************************************************************************"
    def _getMultilineCommentLineStarter(self, path):
        return " * "
    def _getMultilineCommentLastLine(self):
        return " *******************************************************************************/"
    
    def fixLicense(self, path):
        log.info('Processing file: {0}'.format(path))
        licenseText = self.generateLicense(path)
        log.info('License text:{0}{1}'.format(os.linesep, licenseText))
        return


#### Main ####
def main(argv=None):  # IGNORE:C0111
    '''Command line options.'''

    if argv is None:
        argv = sys.argv
    else:
        sys.argv.extend(argv)

    program_name = os.path.basename(sys.argv[0])
    program_version = "v%s" % __version__
    program_build_date = str(__updated__)
    program_version_message = '%%(prog)s %s (%s)' % (program_version, program_build_date)
    program_shortdesc = __import__('__main__').__doc__.split("\n")[1]
    program_license = '''%s

  Created by user_name on %s.
  Copyright 2015 organization_name. All rights reserved.

  Licensed under the Apache License 2.0
  http://www.apache.org/licenses/LICENSE-2.0

  Distributed on an "AS IS" basis without warranties
  or conditions of any kind, either express or implied.

USAGE
''' % (program_shortdesc, str(__date__))

    try:
        # Setup argument parser
        parser = ArgumentParser(description=program_license, formatter_class=RawDescriptionHelpFormatter)
        parser.add_argument("-l", "--license", dest="licenseFile", default="defaultLicense.txt")
        parser.add_argument("-a", "--authors", dest="authorsFile", default="defaultAuthors.txt")
        parser.add_argument("-c", "--contributors", dest="contributorsFile", default="defaultContributors.txt")
        parser.add_argument("-v", "--verbose", dest="verbose", action="count", help="set verbosity level [default: %(default)s]")
        parser.add_argument(dest="paths", help="paths to folder(s) with source file(s) [default: %(default)s]", metavar="path", nargs='+')

        # Process arguments
        args = parser.parse_args()

        paths = args.paths
        verbose = args.verbose
        licenseFile = args.licenseFile
        authorsFile = args.authorsFile
        contributorsFile = args.contributorsFile

        # Set up the log level.
        logLevel = log.INFO
        if verbose > 0:
            logLevel = log.DEBUG
        log.basicConfig(format="%(levelname)s: %(message)s", level=logLevel)

        licenseFixer = LicenseFixer(licenseFile, authorsFile, contributorsFile)
        for inpath in paths:
            licenseFixer.fixLicense(inpath)
        
        return 0
    except KeyboardInterrupt:
        ### handle keyboard interrupt ###
        return 0
    except Exception, e:
        if DEBUG or TESTRUN:
            raise(e)
        indent = len(program_name) * " "
        sys.stderr.write(program_name + ": " + repr(e) + "\n")
        sys.stderr.write(indent + "  for help use --help")
        return 2

if __name__ == "__main__":
    if DEBUG:
        sys.argv.append("-v")
    if TESTRUN:
        import doctest
        doctest.testmod()
    if PROFILE:
        import cProfile
        import pstats
        profile_filename = 'com.bar.foo.pyground.LicenseFixer_profile.txt'
        cProfile.run('main()', profile_filename)
        statsfile = open("profile_stats.txt", "wb")
        p = pstats.Stats(profile_filename, stream=statsfile)
        stats = p.strip_dirs().sort_stats('cumulative')
        stats.print_stats()
        statsfile.close()
        sys.exit(0)
    sys.exit(main())
