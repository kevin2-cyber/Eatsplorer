import 'package:eatsplorer/core/assets.dart';
import 'package:flutter/material.dart';


import 'app/app.dart';
import 'core/constants.dart';

void main() {

  ErrorWidget.builder = (FlutterErrorDetails details) => Material(
    color: Constants.kPrimaryColor,
    child: Center(
      child: Text(
        details.exception.toString(),
        style: const TextStyle(
          color: Colors.white,
          fontWeight: FontWeight.bold,
          fontSize: 20,
          fontFamily: Assets.kFontFamily,
        ),
      ),
    ),
  );


  WidgetsFlutterBinding.ensureInitialized();
  runApp(const Eatsplorer());
}


