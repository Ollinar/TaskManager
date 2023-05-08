package com.saklam.taskmanager.controllers;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.google.zxing.*;

import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.saklam.taskmanager.Database;
import com.saklam.taskmanager.Encryptor;
import com.saklam.taskmanager.models.TaskInfo;
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
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.JOptionPane;

public class QRScannerController implements Initializable {

    @FXML
    private ImageView webcamView;

    ObjectProperty<Image> imageObjectProperty = new SimpleObjectProperty<>();
    Webcam webcam;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webcamView.setTranslateZ(webcamView.getBoundsInLocal().getWidth() / 2.0);
        webcamView.setRotationAxis(Rotate.Y_AXIS);
        webcamView.setRotate(180);
        initWebcam();
//        webcam.setViewSize(new Dimension(400,400));

    }

    private void initWebcam() {
        Task<Void> webcamCapture = new Task<>() {
            @Override
            protected Void call() {
                try {
                    webcam = Webcam.getDefault();
                    webcam.open();
                    final AtomicReference<WritableImage> reference = new AtomicReference<>();
                    while (webcam.isOpen()) {
                        BufferedImage bfi;
                        if ((bfi = webcam.getImage()) != null) {
                            reference.set(SwingFXUtils.toFXImage(bfi, reference.get()));
                            bfi.flush();
                            Platform.runLater(() -> imageObjectProperty.set(reference.get()));

                            LuminanceSource luminanceSource = new BufferedImageLuminanceSource(bfi);
                            BinaryBitmap bmp = new BinaryBitmap(new HybridBinarizer(luminanceSource));

                            try {
                                Result result = new MultiFormatReader().decode(bmp);
                                if (result.getText() != null) {
                                    try {

                                        String beforeSplit = Encryptor.decrypt(result.getText(), "saklam");
                                        String[] temp = beforeSplit.split("<ranilloPogi>");
                                        TaskInfo taskToInsert = new TaskInfo();
                                        taskToInsert.setTaskName(temp[0]);
                                        taskToInsert.setTaskDesc(temp[1]);
                                        taskToInsert.setImprotance(Integer.parseInt(temp[2]));
                                        taskToInsert.setDueDate(Date.valueOf(temp[3]));

                                        Database.insertTask(taskToInsert);
                                        JOptionPane.showMessageDialog(null, "Added Successful", "Message", JOptionPane.INFORMATION_MESSAGE);

                                        break;

                                    } catch (UnsupportedEncodingException | NumberFormatException | InvalidKeyException |
                                             NoSuchAlgorithmException | SQLException | BadPaddingException |
                                             IllegalBlockSizeException | NoSuchPaddingException e) {
                                        Platform.runLater(() -> new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show());
                                    }
                                }

                            } catch (NotFoundException ex) {
                            }

                        }
                    }
                } catch (WebcamException | HeadlessException e) {
                    Platform.runLater(()->new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).show());
                }finally {
                    if (webcam != null)
                        webcam.close();
                }
                Platform.runLater(() -> {
                    ((Stage) (webcamView.getScene().getWindow())).close();
                });
                return null;
            }
        };
        Thread thread = new Thread(webcamCapture);
        thread.setDaemon(true);
        thread.start();
        webcamView.imageProperty().bind(imageObjectProperty);
        
    }
}
