//////////////////////////////
//  Arduino Plant Monitor   
//                          
//  Reads sensor values and 
//  prints to serial port   
//  and wifi.               
//
//  - Imports - 
#include <WiFiEsp.h>        // Library for wifi communication
#include <WiFiEspClient.h>  
#include <WiFiEspServer.h>  
#include <WiFiEspUdp.h>     
#include <SoftwareSerial.h> // Library for serial communication on other ports.
#include <dht11.h>          // Library for DHT11 temperature and airmoisture sensor.

//  - Variables -

//  - Wifi  -
const char ssid[] = "Hotspot";      // Name of the wifi to connect to.
const char password[] = "hotspotpass1"; // Password of above wifi.
const char* host = "192.168.43.207";
const int port = 5482;  
WiFiEspClient client;       
int status = WL_IDLE_STATUS;            // Status of wifi connection.

//  - Pins  - 
const byte rxPin = 7;                 // Wire this to Tx Pin of ESP8266
const byte txPin = 6;                 // Wire this to Rx Pin of ESP8266
const int soilMoistureSensor = A0;    // Soil-moisture sensor on analog pin A0.
const int lightSensor = A1;           // Light sensor on analog pin A1
int soilMoistureValue = 0;            // variable for soil-moisture sensor value
int lightSensorValue = A1;            // variable for the light sensor value
dht11 DHT11;                          // Declares the DHT sensor as an object.
#define DHT11PIN 5                    // Declares the pin number for the DHT sensor.
SoftwareSerial Serial1(rxPin, txPin); // Sets ports as input / output ports for the wifi module.

//  - Setup -
void setup() {
  // Sets serial communication datarate to 9600 baud.
  Serial1.begin(9600);
  Serial.begin(9600); 
  
  // Initializes ESP module
  delay(1000);
  WiFi.init(&Serial1);

  // MAC address 
  byte mac[6];
  WiFi.macAddress(mac);
  char buf[20];
  sprintf(buf, "%02X:%02X:%02X:%02X:%02X:%02X", mac[5], mac[4], mac[3], mac[2], mac[1], mac[0]);

  // Checks if there is a shield
  while (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not present");
    delay(1000);
  }
  
  // Attempts to connect to WiFi network
  Serial.print("Attempting to connect to ssid: ");
  Serial.println(ssid);
  while (status != WL_CONNECTED) { 
    // Connects to WPA/WPA2 network
    status = WiFi.begin(ssid, password);
  }
  printWiFiStatus();
}

//  - Loop  -
void loop() {
  // Soil moisture sensor stuff:
  soilMoistureValue = analogRead(soilMoistureSensor); // reads the value from the soil sensor
  int percentageSoilMoistureValue = map(soilMoistureValue, 1023, 200, 0, 100); // Converts the value to %
  // Light sensor stuff: (!!! Not calibrated yet !!!)
  lightSensorValue = analogRead(lightSensor); // Reads lightsensor value.
  int percentageLightSensorValue = map(lightSensorValue, 1023, 0, 0 ,100); // converts to %
  // Air temperature and humidity sensor stuff:
  int chk = DHT11.read(DHT11PIN); // Reads the value from the sensor.
  int humidityLevel = DHT11.humidity;
  int temperature = DHT11.temperature;
  
  // Prints all values to serial com:
  Serial.print("Soil moisture level (%): ");
  Serial.println(percentageSoilMoistureValue); // Prints the soil moisture level.
  Serial.print("Light level (%):         ");
  Serial.println(percentageLightSensorValue);  // Prints air temperature.
  Serial.print("Air humidity level (%):  ");
  Serial.println((float)DHT11.humidity);       // Prints air humidity level.
  Serial.print("Air temperature (C):     ");
  Serial.println((float)DHT11.temperature);    // Prints air temperature.
  Serial.println("------------------------------");

  delay(1000);

  // WiFi stuff:
  client.connect(host,port);
  if (!client.connected()) {
    Serial.println("-     Connection failed      -");
    return;
  } else {
    Serial.println("-     Success!               -");
    byte mac[6];
    WiFi.macAddress(mac);
    char buf[20];
    sprintf(buf, "%02X:%02X:%02X:%02X:%02X:%02X", mac[5], mac[4], mac[3], mac[2], mac[1], mac[0]);

    // Prints values to server.
    client.println(buf); // Prints MAC address
    client.println(percentageSoilMoistureValue);
    client.println(percentageLightSensorValue);
    client.println(humidityLevel);
    client.println(temperature);
  }
  
  // MAC stuff:
  byte mac[6];
  WiFi.macAddress(mac);
  char buf[20];
  sprintf(buf, "%02X:%02X:%02X:%02X:%02X:%02X", mac[5], mac[4], mac[3], mac[2], mac[1], mac[0]);

  client.stop(); 

  // Print the received signal strength
  long rssi = WiFi.RSSI();
  Serial.print("Signal strength (RSSI): ");
  Serial.println(rssi);

  // Pause before the next read/write cycle.
  delay(20000);
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
