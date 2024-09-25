
const int sensorPin = A0; //sensor is connected to analog pin 
unsigned long previousMillis = 0; //stores last time temp was updated
const long interval = 10000; //measures every 10 seconds

void setup(){
  //serial communication starts on 9600 bauds
  Serial.begin(9600);
}

void loop(){
  //get current time in milliseconds
  unsigned long currentMillis = millis();

  //check if 10 seconds passed
  if (currentMillis - previousMillis >= interval){
    //save current time as last time temp was read
    previousMillis=currentMillis;

    //read analog value from sensor
    int sensorValue = analogRead(sensorPin);
    //convert analog measurement to the voltage value, I use 5V
    float voltage = sensorValue * (5.0 / 1023.0);
    //formel for temperatur in Celsius
    float tempC = (voltage - 0.5) * 10.0; //my sensor is TMP36
    //print the value
    Serial.print(tempC, 2);
    Serial.print(" Celsius, ");
  }
}


