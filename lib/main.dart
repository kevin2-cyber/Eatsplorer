import 'package:flutter/material.dart';

void main() {
  runApp(const Eatsplorer());
}

class Eatsplorer extends StatelessWidget {
  const Eatsplorer({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      theme: ThemeData.light(),
      home: Scaffold(),
    );
  }
}
