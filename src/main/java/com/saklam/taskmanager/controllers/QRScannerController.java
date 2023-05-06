package com.saklam.taskmanager.controllers;

import com.github.sarxos.webcam.Webcam;
import com.google.zxing.*;

import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.saklam.taskmanager.Encryptor;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Rotate;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class QRScannerController implements Initializable {
    @FXML
    private ImageView webcamView;

    ObjectProperty<Image> imageObjectProperty = new SimpleObjectProperty<>();
    Webcam webcam;
    @FXML
    private Label testResult;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webcamView.setTranslateZ(webcamView.getBoundsInLocal().getWidth() / 2.0);
        webcamView.setRotationAxis(Rotate.Y_AXIS);
        webcamView.setRotate(180);
        initWebcam();
//        webcam.setViewSize(new Dimension(400,400));

    }

    private void initWebcam(){
        Task<Void> webcamCapture = new Task<Void>() {
            @Override
            protected Void call(){
                webcam = Webcam.getDefault();
                webcam.open();
                final AtomicReference<WritableImage> reference= new AtomicReference<>();
                BufferedImage bfi;
                boolean onGoing = true;

                while (onGoing){
                    if ((bfi = webcam.getImage()) != null){
                        reference.set(SwingFXUtils.toFXImage(bfi,reference.get()));
                        bfi.flush();
                        Platform.runLater(()->imageObjectProperty.set(reference.get()));

                        LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bfi);
                        BinaryBitmap bmp = new BinaryBitmap(new HybridBinarizer(luminanceSource));
                        try {
                            Result result = new MultiFormatReader().decode(bmp);
                            String temp =  Encryptor.decrypt(result.getText(), "saklam");
                            Platform.runLater(()->testResult.setText(temp));

                        } catch (Exception e) {
                            
                            System.out.println(e.getMessage());
                        }



                    }
                }

                return null;
            }
        };
        Thread thread  =new Thread(webcamCapture);
        thread.setDaemon(true);
        thread.start();
        webcamView.imageProperty().bind(imageObjectProperty);
    }
}
