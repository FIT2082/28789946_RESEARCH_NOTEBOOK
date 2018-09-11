import export
from xml.etree.ElementTree import Element, SubElement, Comment, tostring
import sys
from xml.etree import ElementTree
from xml.dom import minidom
import json
from io import StringIO
import re

# read in database
database = sys.argv[1]
fp = sys.argv[2]
conn = export.create_connection(database)

# nodes
with conn:
    rows = export.select_all_nodes(conn)
    info = export.get_all_info(conn)
d = {}
for row in info:
    d[row[0]] = row[1]

for key in d:
    d[key] = d[key].replace(';\n', "@")
    d[key] = json.loads(d[key])
    d[key] = d[key]['domains'].split('@')
    d[key].pop(-1)

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
        self.backtrack = (False, None)
        self.info = None

# construct tree
tree = []
for node in rows:
    next = Node(node[0], node[1], node, left=node[2] == 0)
    if next.id in d:
        next.info = d[next.id]
    tree.append(next)

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
            cur.backtrack = (True, cur.parent)
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
    if node.id in d and node.data[-2]!= 0:
        for var in d[node.id]:
            if '=' in var:
                var = var.replace(" ", "")
                var = var.split(":")[-1]
                var = var.split('=')
                domain = var[-1]
                var = var[0]
            else:
                var = var.replace(" ", "")
                domain = re.findall(r'\d\.\.\d', var)
                var = var.split(":")[-1]
            varx = SubElement(top, 'new-variable', {'chrono' : str(chrono), 'var':str(var)})
            if domain:
                vals = []
                for elem in domain:
                    if elem[0] == elem[-1]:
                        vals.append(elem[0])
                    else:
                        min1 = int(elem[0])
                        max1 = int(elem[-1])
                        for i in range(min1, (max1 + 1)):
                            vals.append(i)
                # print(vals)
                size = len(vals)
                # print(size)
                str_val = " ".join(map(str, vals))        
                vard = SubElement(varx, 'vardomain', {'min' : str(min(vals)), 'max': str(max(vals)), 'size': str(size)})
                values = SubElement(vard, 'values')
                values.text = str_val
    if node.backtrack[0]:
        chrono +=1
        backtrack = SubElement(top, 'back-to', {'chrono':str(chrono), 'node':str(node.id), 'node-before':str(node.backtrack[1])})
    if node.data[-2] != 0:
        chrono +=1
        cident +=1
        node.cident = cident
        new_cons = SubElement(top, 'new-constraint', {'chrono' : str(chrono), 'cident': 'c' + str(cident), 'cinternal':node.data[-1]})
    # if solve, report solved state
    if node.data[-2] == 0:
        chrono +=1
        solution = SubElement(top, 'solved', {'chrono' : str(chrono), 'cident': 'c' + str(cident)})
        # get solution
        state = SubElement(solution, 'state')
        for sol in d[node.id]:
            print(sol)
            sol = sol.replace(" ", "")
            sol = sol.split(":")[-1]
            sol = sol.split('=')
            domain = sol[-1]
            sol = sol[0]
            vartag = SubElement(state, 'variable', {'vname': str(sol)})
            vard = SubElement(vartag, 'vardomain', {'min' :str(domain), 'max': str(domain), 'size': '1'})
            values = SubElement(vard, 'values')
            values.text = domain
            
# pretty prints XML   
def prettify(elem):
    rough_string = ElementTree.tostring(elem, 'utf-8')
    reparsed = minidom.parseString(rough_string)
    return reparsed.toprettyxml(indent="  ")

# write to file
with open(fp, 'w') as out:
    out.write(prettify(top))