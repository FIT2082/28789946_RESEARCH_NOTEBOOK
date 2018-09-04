import export
from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import sys
from xml.etree import ElementTree
from xml.dom import minidom

# read in database
database = sys.argv[1]
fp = sys.argv[2]
conn = export.create_connection(database)

with conn:
    rows = export.select_all_nodes(conn)

# node object
class Node:
    def __init__(self, id, parent, data, left=False):
        self.id = id
        self.parent = parent
        self.left = left
        self.data = data
        self.rc = None
        self.lc = None
        self.cident = None
        # self.backtrack = False

# construct tree
tree = []
for node in rows:
    tree.append(Node(node[0], node[1], node, left=node[2] == 0))

# set children nodes
for node in tree:
    if node.parent >= 0:
        if node.left:
            tree[node.parent].lc = node.id
        else:
            tree[node.parent].rc = node.id

# left preorder traversal
def dfs(tree):
    s = []
    path = []
    s.append(tree[0])
    while len(s) > 0:
        cur = s.pop()
        path.append(cur)
        if cur.rc is not None:
            s.append(tree[cur.rc])
        if cur.lc is not None:
            s.append(tree[cur.lc])
        # if no kids, sets backtrack to true
        if cur.data[-3] == 0:
            self.backtrack = (True, cur.id)
    return path
path = dfs(tree)

# xml definitions
top = Element('gentra4cp')
header = SubElement(top, 'header')
date = SubElement(header, 'date')
creator = SubElement(header, 'creator')
provide = SubElement(top, 'provide')

# generate xml document
chrono = 0
cident = 0
depth = 0
for node in path:
    choicepoint = SubElement(top, 'choice-point', {'chrono' : str(chrono), 'nident':str(node.id)})
    # if not solved, create new constraint
    if node.backtrack[0]:
        chrono +=1
        backtrack = SubElement(top, 'back-to', {'chrono':str(chrono), 'node':str(node.id), 'node-before':str(node.backtrack[1])})
    if node.data[-2] != 0:
        chrono +=1
        cident +=1
        node.cident = cident
        new_cons = SubElement(top, 'new-constraint', {'chrono' : str(chrono), 'cident': 'c' + str(cident), 'cinternal':node.data[-1]})
    # if solve, report solved state
    else:
        chrono +=1
        solution = SubElement(top, 'solved', {'chrono' : str(chrono), 'cident': 'c' + str(cident)})

# pretty prints XML   
def prettify(elem):
    rough_string = ElementTree.tostring(elem, 'utf-8')
    reparsed = minidom.parseString(rough_string)
    return reparsed.toprettyxml(indent="  ")
    
# writes to file
with open(fp, 'w') as out:
    out.write(prettify(top))