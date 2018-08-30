import export
from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import sys
from xml.dom import minidom


# read in tree 
database = sys.argv[1]
conn = export.create_connection(database)

with conn:
    rows = export.select_all_nodes(conn)
tree =  [[] for i in range(len(rows))]
for row in rows:
    tree[row[1] + 1].append(row)

# preorder dfs
def dfs(root):
    if root:
        s = []
        s.insert(0, root)
        while s:
            cur = s.pop()
            print(cur)
            if len(tree) > cur[0] + 1:
                if len(tree[cur[0] + 1])>1:
                    s.insert(0, tree[cur[0] + 1][1])
                if len(tree[cur[0] + 1]) > 0:
                    s.insert(0, tree[cur[0] + 1][0])
    
# print(tree)
dfs(tree[0][0])

top = Element('gentra4cp')
header = SubElement(top, 'header')
provide = SubElement(top, 'provide')
dfs(root)




def prettify(elem):
    """Return a pretty-printed XML string for the Element.
    """
    rough_string = ElementTree.tostring(elem, 'utf-8')
    reparsed = minidom.parseString(rough_string)
    return reparsed.toprettyxml(indent="  ")