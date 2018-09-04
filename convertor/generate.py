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
    cident = 0
    chrono = 0
    if root:
        s = []
        s.insert(0, root)
        while s:
            cur = s.pop()
            print(cur)
            nident = cur[0]
            chrono += 1
            choicepoint = SubElement(top, 'choice-point', {'chrono' : str(chrono), 'nident':str(nident)})
            if cur[-2] != 0:
                new_cons = SubElement(top, 'new-constraint', {'chrono' : str(chrono), 'cident': 'c' + str(cident), 'cinternal':str(cur[-1])})
            else:
                solution = SubElement(top, 'solved', {'chrono' : str(chrono), 'cident': 'c' + str(cident)})
                cident +=1
            if len(tree) > cur[0] + 1:
                if len(tree[cur[0] + 1])>0:
                    # if(cur[-2] != 4 or cur[-2] != 3 or cur[-2] != 1):
                    s.insert(0, tree[cur[0] + 1][0])
                    # else:
                        
                if len(tree[cur[0] + 1]) > 1:
                    # if(cur[-2] != 4 or cur[-2] != 3 or cur[-2] != 1):
                    s.insert(0, tree[cur[0] + 1][1])
    
# print(tree)
def prettify(elem):

    rough_string = ElementTree.tostring(elem, 'utf-8')
    reparsed = minidom.parseString(rough_string)
    return reparsed.toprettyxml(indent="  ")



def dfs2(root):
    if root:
        s = []
        s.insert(0, root)
        while s:
            cur = s.pop()
            print(cur[0])
            if len(tree) > cur[0] + 1:
                s.insert(0, tree[cur[0] + 1][0])
            if len(tree[cur[0] + 1]) > 1:
                s.insert(0, tree[cur[0] + 1][1])

# dfs(tree[0][0], top)
dfs2(tree[0][0])
print(prettify(top))
# print(top)



