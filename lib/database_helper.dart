
import 'package:sqflite/sqflite.dart';
import 'package:path/path.dart';

class DatabaseHelper {
  static const _databaseName = "FuelUsageAnalyzer.db";
  static const _databaseVersion = 1;

  static const table = 'FuelUsageRecord';

  static const columnDate = 'date';
  static const columnOdo = 'odo';
  static const columnAmount = 'amount';
  static const columnPrice = 'price';
  static const columnPerPrice = 'perprice';

  // make this a singleton class
  DatabaseHelper._privateConstructor();
  static final DatabaseHelper instance = DatabaseHelper._privateConstructor();

  // only have a single app-wide reference to the database
  static Database? _database;
  Future<Database> get database async {
    if (_database != null) return _database!;
    _database = await _initDatabase();
    return _database!;
  }

  // this opens the database (and creates it if it doesn't exist)
  _initDatabase() async {
    String path = join(await getDatabasesPath(), _databaseName);
    return await openDatabase(path,
        version: _databaseVersion, onCreate: _onCreate);
  }

  // SQL code to create the database table
  Future _onCreate(Database db, int version) async {
    await db.execute('''
          CREATE TABLE $table (
            $columnDate TEXT PRIMARY KEY,
            $columnOdo TEXT NOT NULL,
            $columnAmount TEXT NOT NULL,
            $columnPrice TEXT NOT NULL,
            $columnPerPrice TEXT NOT NULL
          )
          ''');
  }
}
