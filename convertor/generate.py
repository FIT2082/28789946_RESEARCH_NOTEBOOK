import export
from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import sys
from xml.etree import ElementTree

from xml.dom import minidom


# read in tree 
database = sys.argv[1]
conn = export.create_connection(database)

with conn:
    rows = export.select_all_nodes(conn)
tree =  [[] for i in range(len(rows))]
for row in rows:
    tree[row[1] + 1].append(row)

top = Element('gentra4cp')
header = SubElement(top, 'header')
provide = SubElement(top, 'provide')

# preorder dfs
def dfs(root, top):
    if root:
        s = []
        s.insert(0, root)
        while s:
            cur = s.pop()
            choicepoint = SubElement(top, 'choice-point')
            choicepoint.text = str(cur)
            new_cons = SubElement(top, 'new-constraint')
            new_cons.text = str(cur[-1])
            if len(tree) > cur[0] + 1:
                if len(tree[cur[0] + 1])>0:
                    s.insert(0, tree[cur[0] + 1][0])
                if len(tree[cur[0] + 1]) > 1:
                    s.insert(0, tree[cur[0] + 1][1])
    
# print(tree)
def prettify(elem):
    """Return a pretty-printed XML string for the Element.
    """
    rough_string = ElementTree.tostring(elem, 'utf-8')
    reparsed = minidom.parseString(rough_string)
    return reparsed.toprettyxml(indent="  ")

dfs(tree[0][0], top)
print(prettify(top))




def prettify(elem):
    """Return a pretty-printed XML string for the Element.
    """
    rough_string = ElementTree.tostring(elem, 'utf-8')
    reparsed = minidom.parseString(rough_string)
    return reparsed.toprettyxml(indent="  ")