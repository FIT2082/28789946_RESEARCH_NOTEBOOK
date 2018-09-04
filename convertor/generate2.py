import export
from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import sys
from xml.etree import ElementTree
from xml.dom import minidom


# read in tree 
database = sys.argv[1]
conn = export.create_connection(database)