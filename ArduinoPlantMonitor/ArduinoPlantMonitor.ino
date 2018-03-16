//////////////////////////////
//  Arduino Plant Monitor   //
//                          //
//  Reads sensor values and //
//  prints to serial port.  // 
//                          //
//////////////////////////////

// Imports
#include <dht11.h>    // Library for DHT11 temperature and airmoisture sensor.

// Variables
const int soilMoistureSensor = A0;  // Soil-moisture sensor on analog pin A0.
int soilMoistureValue = 0;          // variable to store the value coming from the sensor

dht11 DHT11;          // Declares the DHT sensor as an object.
#define DHT11PIN 7    // Declares the pin number for the DHT sensor.

// Main
void setup() {
  pinMode(ledPin, OUTPUT); // Declares the arduino LED as an output.
  Serial.begin(9600);      // Sets serial communication datarate to 9600 baud.
}

void loop() {

  // Soil moisture sensor stuff.
  soilMoistureValue = analogRead(soilMoistureSensor); // reads the value from the soil sensor
  int percentageSoilMoistureValue = map(soilMoistureValue, 1023, 200, 0, 100); // Converts the value to %
  
  // Air temperature and humidity sensor stuff.
  int chk = DHT11.read(DHT11PIN); // Reads the value from the sensor.

  // Prints all values
  Serial.print("Soil moisture level (%): ");
  Serial.println(percentageSoilMoistureValue); // Prints the soil moisture level.
  
  Serial.print("Air humidity level (%):  ");
  Serial.println((float)DHT11.humidity);    // Prints air humidity level.

  Serial.print("Air temperature (C):     ");
  Serial.println((float)DHT11.temperature); // Prints air temperature.
  Serial.println("------------------------------");

  // Pause before next reading.
  delay(5000);
}
