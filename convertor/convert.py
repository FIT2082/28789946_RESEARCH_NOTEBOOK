#!/usr/bin/python

import sqlite3
import sys
import time

def outputJSONObject(key, val, last):
	if val is None:
		val = ''
	obj = ''
	obj += '\t\t"' + str(key).lower() + '" : '
	obj += '"' + str(val) + '"'
	if last != True:
		obj+=','
	obj+='\n'
	return obj

if len(sys.argv) < 2:
	print("Error: Need to pass in SQLite database file to convert")
	sys.exit()

db = str(sys.argv[1])
save_dir = sys.argv[2]

con = sqlite3.connect(db)
con.row_factory = sqlite3.Row

cur = con.cursor()
cur.execute("SELECT name FROM sqlite_master WHERE type='table';")

tables = [item[0] for item in cur.fetchall()]

startTime = time.time()
for table in tables:
	cur = con.cursor()
	cur.execute("select * from " + table)

	col_names = [cn[0] for cn in cur.description]

	with open(save_dir + table + ".json", "w") as f:
		f.write("[\n")

		items = cur.fetchall()
		for i, item in enumerate(items):
			if item == None:
				break
			json = '\t{\n'
			for j, col in enumerate(col_names):
				last = j == (len(col_names)-1)
				json += outputJSONObject(col, item[col], last)

			if i != (len(items)-1):
				json += '\t},\n'
			else:
				json += '\t}\n'
			f.write(json)
		f.write("]")
	cur.close()
endTime = time.time()
totalTime = endTime - startTime

print("SQLite to JSON conversion took {0:.2f} seconds".format(totalTime))
