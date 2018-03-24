
#include <SoftwareSerial.h>
#include "WiFiEsp.h"

SoftwareSerial Serial1(6,7);

char ssid[] = "v a t t e n";
char password[] = "labiblioteca";
int status = WL_IDLE_STATUS;

void setup() {
  //initialize serial for debugging
  Serial.begin(115200);

  //initialize serial for ESP module
  Serial1.begin(9600);

  //initialize ESP module
  WiFi.init(&Serial1);
  
  //check for the presense of the shield
  while (WiFi.status()==WL_NO_SHIELD){
    Serial.println("WiFi shield not present");
    delay(1000);  
    
  }

  //Attempt to connect to WiFi network
  while(status != WL_CONNECTED){
    Serial.print("Attempting to connect to ssid: ");
    Serial.println(ssid);

    //Connect to WPA/WPA2 network
    status = WiFi.begin(ssid, password);
  }

  Serial.println("You're connected to the network");
  printWiFiStatus();
}

void loop() {
// print the received signal strength
  long rssi = WiFi.RSSI();
  Serial.print("Signal strength (RSSI): ");
  Serial.println(rssi);
  delay(10000);
}

/**
 * prints the ssid of the network, Arduinos IPv4-address
 * and MAC-Address
 */
void printWiFiStatus(){
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

