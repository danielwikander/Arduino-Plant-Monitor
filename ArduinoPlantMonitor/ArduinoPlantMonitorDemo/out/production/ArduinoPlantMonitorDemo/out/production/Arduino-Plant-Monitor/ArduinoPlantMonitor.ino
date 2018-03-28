//////////////////////////////
//  Arduino Plant Monitor   //
//                          //
//  Reads sensor values and //
//  prints to serial port   //
//  and wifi.               //
//////////////////////////////

// Imports
#include <SoftwareSerial.h> // Library for serial communication on other ports.
#include <dht11.h>          // Library for DHT11 temperature and airmoisture sensor.
#include "WiFiEsp.h"        // Library for ESP8266 wifi module.

// Variables
SoftwareSerial Serial1(6, 7);     // Sets ports 6(rx) & 7(tx) as input / output ports for the wifi module.
char ssid[] = "v a t t e n";      // Name of the wifi to connect to.
char password[] = "labiblioteca"; // Passord of above wifi.
int status = WL_IDLE_STATUS;      // Status of wifi connection.
WiFiEspServer server(80);         // Creates server on port 80;

const int soilMoistureSensor = A0; // Soil-moisture sensor on analog pin A0.
const int lightSensor = A1;        // Light sensor on analog pin A1
int soilMoistureValue = 0;         // variable for soil-moisture sensor value
int lightSensorValue = A1;         // variable for the light sensor value

dht11 DHT11;       // Declares the DHT sensor as an object.
#define DHT11PIN 5 // Declares the pin number for the DHT sensor.

// Main
void setup() {
  Serial1.begin(115200); 
  Serial.begin(9600); // Sets serial communication datarate to 9600 baud.
  Serial1.write("AT+UART_DEF=9600,8,1,0,0\r\n");
  
  // Initializes ESP module
  WiFi.init(&Serial1);

  // Checks if there is a shield
  while (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    delay(1000);
  }
  // Attempts to connect to WiFi network
  while (status != WL_CONNECTED) {
    Serial.print("Attempting to connect to ssid: ");
    Serial.println(ssid);

    // Connects to WPA/WPA2 network
    status = WiFi.begin(ssid, password);
  }

  Serial.println("You're connected to the network");
  printWiFiStatus();

  server.begin();
}

void loop() {

  // Soil moisture sensor stuff:
  soilMoistureValue = analogRead(soilMoistureSensor); // reads the value from the soil sensor
  int percentageSoilMoistureValue = map(soilMoistureValue, 1023, 200, 0, 100); // Converts the value to %

  // Light sensor stuff: (!!! Not calibrated yet !!!)
  lightSensorValue = analogRead(lightSensor); // Reads lightsensor value.
  int percentageLightSensorValue = map(lightSensorValue, 1023, 200, 0 ,100); // converts to % 
  
  // Air temperature and humidity sensor stuff:
  int chk = DHT11.read(DHT11PIN); // Reads the value from the sensor.

  // Prints all values to serial com:
  Serial.print("Soil moisture level (%): ");
  Serial.println(percentageSoilMoistureValue); // Prints the soil moisture level.

  Serial.print("Light level (%):         ");
  Serial.println(percentageLightSensorValue);    // Prints air temperature.

  Serial.print("Air humidity level (%):  ");
  Serial.println((float)DHT11.humidity);       // Prints air humidity level.

  Serial.print("Air temperature (C):     ");
  Serial.println((float)DHT11.temperature);    // Prints air temperature.
  Serial.println("------------------------------");

  delay(2000);
  // WiFi stuff:
  WiFiEspClient client = server.available();

  if (client) {
    Serial.println("New client connected");

    if (client.connected()) {
      if (client.available()) {
        client.print("Soil moisture level (%): ");
        client.println(percentageSoilMoistureValue);
        client.print("Light level (%):         ");
        client.println(percentageLightSensorValue);
        client.print("Air humidity level (%):  ");
        client.println((float)DHT11.humidity);
        client.print("Air temperature (C):     ");
        client.println((float)DHT11.temperature);
      }
    }
  }

  // Print the received signal strength
  long rssi = WiFi.RSSI();
  Serial.print("Signal strength (RSSI): ");
  Serial.println(rssi);
  
  // Pause before the next read/write cycle.
  delay(10000);
}


// Prints the ssid of the network, Arduinos IPv4-address and MAC-Address.
void printWiFiStatus() {
  Serial.print("SSID :");
  Serial.println(WiFi.SSID());

  IPAddress ip = WiFi.localIP();
  Serial.print("IP Address: ");
  Serial.println(ip);

  //MAC
  byte mac[6];
  WiFi.macAddress(mac);
  char buf[20];
  sprintf(buf, "%02X:%02X:%02X:%02X:%02X:%02X", mac[5], mac[4], mac[3], mac[2], mac[1], mac[0]);
  Serial.print("MAC address: ");
  Serial.println(buf);
}
