
import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

import 'database_helper.dart';
import 'main.dart';

class DetailScreen extends StatelessWidget {
  const DetailScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Fuel Usage Records'),
      ),
      body: Consumer<FuelUsageModel>(
        builder: (context, model, child) {
          return ListView.builder(
            itemCount: model.records.length,
            itemBuilder: (context, index) {
              final record = model.records[index];
              return ListTile(
                title: Text('Odo: ${record[DatabaseHelper.columnOdo]}'),
                subtitle: Text('Amount: ${record[DatabaseHelper.columnAmount]}, Price: ${record[DatabaseHelper.columnPrice]}'),
              );
            },
          );
        },
      ),
    );
  }
}
