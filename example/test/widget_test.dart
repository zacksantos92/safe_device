import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:safe_device_example/main.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  const channel = MethodChannel('safe_device');

  // Native responses for a real, non-rooted device with developer mode on.
  const nativeResults = <String, Object?>{
    'init': null,
    'isJailBroken': false,
    'isRealDevice': true,
    'isMockLocation': false,
    'isOnExternalStorage': false,
    'isDevelopmentModeEnable': true,
  };

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(
            channel, (call) async => nativeResults[call.method]);
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger
        .setMockMethodCallHandler(channel, null);
  });

  /// Finds the value text rendered next to [label] by `buildInfoRow`.
  Finder valueOf(String label) => find.descendant(
        of: find.ancestor(of: find.text(label), matching: find.byType(Row)),
        matching: find.byWidgetPredicate(
          (widget) => widget is Text && widget.data != label,
        ),
      );

  testWidgets('shows the result of each safety check',
      (WidgetTester tester) async {
    await tester.pumpWidget(const MyApp());
    await tester.pumpAndSettle();

    expect(find.text('Device Safe Check'), findsOneWidget);

    const expected = <String, bool>{
      'isJailBroken()': false,
      'isMockLocation()': false,
      'isRealDevice()': true,
      'isOnExternalStorage()': false,
      'isSafeDevice()': true,
      'isDevelopmentModeEnable()': true,
    };

    for (final MapEntry(key: label, value: value) in expected.entries) {
      final valueText = tester.widget<Text>(valueOf(label));
      expect(valueText.data, '$value', reason: label);
    }
  });
}
