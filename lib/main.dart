import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:share_plus/share_plus.dart';

import 'database_helper.dart';
import 'detail_screen.dart';

void main() {
  runApp(
    ChangeNotifierProvider(
      create: (context) => FuelUsageModel(),
      child: const FuelUsageAnalyzerApp(),
    ),
  );
}

class FuelUsageAnalyzerApp extends StatelessWidget {
  const FuelUsageAnalyzerApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Fuel Usage Analyzer',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const FuelUsageAnalyzerHome(),
    );
  }
}

class FuelUsageAnalyzerHome extends StatefulWidget {
  const FuelUsageAnalyzerHome({super.key});

  @override
  State<FuelUsageAnalyzerHome> createState() => _FuelUsageAnalyzerHomeState();
}

class _FuelUsageAnalyzerHomeState extends State<FuelUsageAnalyzerHome> {
  @override
  void initState() {
    super.initState();
    Provider.of<FuelUsageModel>(context, listen: false).getRecords();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Fuel Usage Analyzer'),
        actions: <Widget>[
          PopupMenuButton<String>(
            onSelected: (value) {
              final model = Provider.of<FuelUsageModel>(context, listen: false);
              if (value == 'new') {
                model.setRegistrationMode(true);
              } else if (value == 'edit') {
                model.setRegistrationMode(false);
              } else if (value == 'view') {
                Navigator.push(
                  context,
                  MaterialPageRoute(builder: (context) => const DetailScreen()),
                );
              } else if (value == 'export') {
                model.exportRecords();
              } else if (value == 'reset') {
                model.deleteAllRecords();
              }
            },
            itemBuilder: (BuildContext context) {
              return [
                const PopupMenuItem(
                  value: 'new',
                  child: Text('New Record'),
                ),
                const PopupMenuItem(
                  value: 'edit',
                  child: Text('Edit Record'),
                ),
                const PopupMenuItem(
                  value: 'view',
                  child: Text('View Records'),
                ),
                const PopupMenuItem(
                  value: 'export',
                  child: Text('Export'),
                ),
                const PopupMenuItem(
                  value: 'reset',
                  child: Text('Reset'),
                ),
              ];
            },
          ),
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Consumer<FuelUsageModel>(
          builder: (context, model, child) {
            return Column(
              children: <Widget>[
                if (!model.isReg)
                  Text('Record ${model.currentIndex + 1} of ${model.records.length}'),
                TextField(
                  controller: model.odoController,
                  decoration: const InputDecoration(
                    labelText: 'Odometer',
                  ),
                  keyboardType: TextInputType.number,
                ),
                TextField(
                  controller: model.amountController,
                  decoration: const InputDecoration(
                    labelText: 'Fuel Amount',
                  ),
                  keyboardType: TextInputType.number,
                ),
                TextField(
                  controller: model.priceController,
                  decoration: const InputDecoration(
                    labelText: 'Price',
                  ),
                  keyboardType: TextInputType.number,
                ),
                const SizedBox(height: 16.0),
                ElevatedButton(
                  onPressed: () {
                    if (model.isReg) {
                      model.saveRecord();
                    } else {
                      model.updateRecord();
                    }
                  },
                  child: Text(model.isReg ? 'Save' : 'Update'),
                ),
                if (!model.isReg)
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                    children: <Widget>[
                      ElevatedButton(
                        onPressed: () {
                          model.previousRecord();
                        },
                        child: const Text('<<'),
                      ),
                      ElevatedButton(
                        onPressed: () {
                          model.nextRecord();
                        },
                        child: const Text('>>'),
                      ),
                    ],
                  ),
              ],
            );
          },
        ),
      ),
    );
  }
}

class FuelUsageModel extends ChangeNotifier {
  final dbHelper = DatabaseHelper.instance;

  List<Map<String, dynamic>> _records = [];
  int _currentIndex = 0;
  bool _isReg = true;

  final odoController = TextEditingController();
  final amountController = TextEditingController();
  final priceController = TextEditingController();

  List<Map<String, dynamic>> get records => _records;
  int get currentIndex => _currentIndex;
  bool get isReg => _isReg;

  void setRegistrationMode(bool isReg) {
    _isReg = isReg;
    if (_isReg) {
      odoController.clear();
      amountController.clear();
      priceController.clear();
    } else {
      if (_records.isNotEmpty) {
        _updateControllers();
      }
    }
    notifyListeners();
  }

  Future<void> saveRecord() async {
    final odo = odoController.text;
    final amount = amountController.text;
    final price = priceController.text;

    if (odo.isEmpty || amount.isEmpty || price.isEmpty) {
      return;
    }

    final perPrice = double.parse(price) / double.parse(amount);

    final record = {
      DatabaseHelper.columnDate: DateTime.now().toIso8601String(),
      DatabaseHelper.columnOdo: odo,
      DatabaseHelper.columnAmount: amount,
      DatabaseHelper.columnPrice: price,
      DatabaseHelper.columnPerPrice: perPrice.toString(),
    };

    final db = await dbHelper.database;
    await db.insert(DatabaseHelper.table, record);

    odoController.clear();
    amountController.clear();
    priceController.clear();

    getRecords();
  }

  Future<void> updateRecord() async {
    final odo = odoController.text;
    final amount = amountController.text;
    final price = priceController.text;

    if (odo.isEmpty || amount.isEmpty || price.isEmpty) {
      return;
    }

    final perPrice = double.parse(price) / double.parse(amount);

    final record = {
      DatabaseHelper.columnOdo: odo,
      DatabaseHelper.columnAmount: amount,
      DatabaseHelper.columnPrice: price,
      DatabaseHelper.columnPerPrice: perPrice.toString(),
    };

    final db = await dbHelper.database;
    await db.update(
      DatabaseHelper.table,
      record,
      where: '${DatabaseHelper.columnDate} = ?',
      whereArgs: [_records[_currentIndex][DatabaseHelper.columnDate]],
    );

    getRecords();
  }

  Future<void> getRecords() async {
    final db = await dbHelper.database;
    _records = await db.query(DatabaseHelper.table);
    if (_records.isNotEmpty) {
      _currentIndex = _records.length - 1;
      if (!_isReg) {
        _updateControllers();
      }
    }
    notifyListeners();
  }

  void _updateControllers() {
    if (_records.isNotEmpty) {
      final record = _records[_currentIndex];
      odoController.text = record[DatabaseHelper.columnOdo];
      amountController.text = record[DatabaseHelper.columnAmount];
      priceController.text = record[DatabaseHelper.columnPrice];
    }
  }

  void nextRecord() {
    if (_currentIndex < _records.length - 1) {
      _currentIndex++;
      _updateControllers();
      notifyListeners();
    }
  }

  void previousRecord() {
    if (_currentIndex > 0) {
      _currentIndex--;
      _updateControllers();
      notifyListeners();
    }
  }

  Future<void> exportRecords() async {
    String csv = 'Date,Odometer,Amount,Price,Price/Amount\n';
    for (var record in _records) {
      csv += '${record[DatabaseHelper.columnDate]},${record[DatabaseHelper.columnOdo]},${record[DatabaseHelper.columnAmount]},${record[DatabaseHelper.columnPrice]},${record[DatabaseHelper.columnPerPrice]}\n';
    }
    Share.share(csv, subject: 'Fuel Usage Records');
  }

  Future<void> deleteAllRecords() async {
    final db = await dbHelper.database;
    await db.delete(DatabaseHelper.table);
    _records = [];
    odoController.clear();
    amountController.clear();
    priceController.clear();
    notifyListeners();
  }
}