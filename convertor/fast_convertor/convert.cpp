#include "CppSQLite.h"
#include <ctime>
#include <iostream>
using namespace std;

int main(int argc, char** argv)
{
    try
    {
        int i, fld;
        time_t tmStart, tmEnd;
        CppSQLiteDB db;

        cout << "SQLite Version: " << db.SQLiteVersion() << endl;

        db.open(gszFile);
        cout << db.execScalar("select count(*) from emp;") 
               << " rows in emp table in ";
        db.Close();
    }
    catch (CppSQLiteException& e)
    {
        cerr << e.errorCode() << ":" << e.errorMessage() << endl;
    }
}

