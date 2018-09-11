#!/usr/bin/python
 
import sqlite3
import sys
from sqlite3 import Error
 
 
def create_connection(db_file):
    """ 
    create a database connection to the SQLite database
    specified by the db_file
    :param db_file: database file
    :return: Connection object or None
    """
    try:
        conn = sqlite3.connect(db_file)
        return conn
    except Error as e:
        print(e)
 
    return None
 
 
def select_all_nodes(conn):
    """
    Query all rows in the Node table
    :param conn: the Connection object
    :return:
    """
    cur = conn.cursor()
    cur.execute("SELECT * FROM Nodes")
 
    rows = cur.fetchall()
    return rows

def get_all_info(conn):
    """
    """
    cur = conn.cursor()
    cur.execute("SELECT * FROM Info")
    rows = cur.fetchall()
    return rows
    

def select_task_by_status(conn, status):
    """
    Query nodes by priority
    :param conn: the Connection object
    :param priority:
    :return:
    """
    cur = conn.cursor()
    cur.execute("SELECT * FROM nodes WHERE status=?", (status,))
 
    rows = cur.fetchall()
    return rows

def select_node_by_label(conn, label):
    """
    Query tasks by label
    :param conn: the Connection object
    :param label: label {0, 1, 2, 3, 4}
    """
    cur = conn.cursor()
    cur.execute("SELECT * FROM Nodes WHERE label=?", (label,)) 


def get_info_by_node(conn, node):
    """
    Queries Info database for information on node
    :param conn: the Connnection object
    :param node: nodeid number
    """ 
    cur = conn.cursor()
    cur.execute("SELECT * FROM Info WHERE NodeID=?", (node))


# def write(fn):
#     """
#     Writes db output to file
#     :param fn: output filename
#     """
#     with open(fn, 'w') as out
#         out.write


def main():
    database = sys.argv[1]
    # create a database connection
    conn = create_connection(database)
    with conn:
        # print("1. Query task by status:")
        # select_task_by_status(conn,1)
 
        tmp =  select_all_nodes(conn)
 
        for i in tmp:
            print(i)
if __name__ == '__main__':
    main()
