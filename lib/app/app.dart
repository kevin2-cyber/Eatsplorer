import 'package:eatsplorer/app/presentation/home.dart';
import 'package:flutter/material.dart';

class Eatsplorer extends StatelessWidget {
  const Eatsplorer({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      themeMode: ThemeMode.system,
      theme: ThemeData(
        fontFamily: 'Samsung Sharp Sans',
      ),
      home: const Home(),
    );
  }
}