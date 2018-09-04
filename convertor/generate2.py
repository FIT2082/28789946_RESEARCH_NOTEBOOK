import export
from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import sys
from xml.etree import ElementTree
from xml.dom import minidom




# read in database
database = sys.argv[1]
conn = export.create_connection(database)

with conn:
    rows = export.select_all_nodes(conn)

# node object
class Node:
    def __init__(self, id, parent, left=False):
        self.id = id
        self.parent = parent
        self.left = left
        self.rc = None
        self.lc = None

# construct tree
tree = []
for node in rows:
    tree.append(Node(node[0], node[1], left=node[2] == 0))
    
for node in tree:
    if node.parent >= 0:
        if node.left:
            tree[node.parent].lc = node.id
        else:
            tree[node.parent].rc = node.id
for node in tree:
    print(node.id, node.lc, node.rc)

def dfs(tree):
    s = []
    s.append(tree[0])
    while len(s) > 0:
        cur = s.pop()
        print(cur.id)
        if cur.rc is not None:
            s.append(tree[cur.rc])
        if cur.lc is not None:
            s.append(tree[cur.lc])
            
dfs(tree)
