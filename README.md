ec504project
============

ANDROID: https://github.com/zerohhy/android_jpegs_to_mpeg

VideoEncoder: Group 13

To run, open project in Eclipse, go to src > jpegCompression > CompressionJpeg.java and run.

Current Capabilities: Our current prototype reads in a single JPEG file, extracts the RGBA information from the file into a three-dimensional matrix, compresses this raw data using a discrete cosine transform algorithm, and writes the non-zero values to a CSV file.

Ultimate Goal: Ability to encode up to 100 JPEG images (of the same dimensions) into one encoded file in under 5 minutes. 
The file size should never be more than the sum of the individual files.
Ability to play back the images from the encoded file at least 10 images per second.
Provide a command-line User Interface for encoding and viewing. Format should be:
encode [file1] [file2] ... [filen] --output [outputFile]: encode file1...filen, in order, into the given output file
view [outputFile]: display the images encoded in outputFile, at least 10 images per second
