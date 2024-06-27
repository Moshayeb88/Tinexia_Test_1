
import sqlite3

def vulnerable_function(input_data):
    conn = sqlite3.connect('example.db')
    cursor = conn.cursor()
    
    # Vulnerable SQL query construction
    query = "SELECT * FROM users WHERE username = '" + input_data + "'"
    
    cursor.execute(query)
    rows = cursor.fetchall()
    
    for row in rows:
        print(row)
    
    conn.close()

if __name__ == "__main__":
    user_input = input("Enter username to search: ")
    vulnerable_function(user_input)
