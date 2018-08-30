import export
import sys

# read in tree 
database = sys.argv[1]
conn = export.create_connection(database)

with conn:
    rows = export.select_all_nodes(conn)
tree =  [[] for i in range(len(rows))]
for row in rows:
    tree[row[1] + 1].append(row)


def dfs(root):
    if root:
        s = []
        s.insert(0, root)
        while s:
            cur = s.pop()
            print(cur)
            if len(tree[cur[0] + 1])>1:
                s.insert(0, tree[cur[0] + 1][1])
            if len(tree[cur[0] + 1]) > 0:
                s.insert(0, tree[cur[0] + 1][0])
    
# print(tree)
dfs(tree[0][0])

# for level in tree:
    # print(level)