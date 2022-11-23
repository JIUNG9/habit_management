// React Native Bottom Navigation
// https://aboutreact.com/react-native-bottom-navigation/

import React from "react";
import {
    StyleSheet,
    Text,
    View,
    SafeAreaView,
    TouchableOpacity,
    Image,
    Button,
} from "react-native";
import { Camera, CameraType } from "expo-camera";
import HabitPictureSampleContainer from "../../container/UC-02-Record/HabitPictureSampleContainer";

const HabitPictureScreen = (props) => {
    const {
        navigation,
        type,
        setType,
        permission,
        requestPermission,
        image,
        setImage,
        captureVisible,
        setCaptureVisible,
        toggleCameraType,
        pickImage,
        snapPicture,
        myCameraRef,
        samplePictureScreen,
        setSamplePictureScreen,
    } = props;

    // if (!permission) {
    //     // Camera permissions are still loading
    //     return <View />;
    // }

    // if (!permission.granted) {
    //     // Camera permissions are not granted yet
    //     return (
    //         <View style={styles.container}>
    //             <Text style={{ textAlign: "center" }}>
    //                 We need your permission to show the camera
    //             </Text>
    //             <Button onPress={requestPermission} title="grant permission" />
    //         </View>
    //     );
    // }

    return (
        <SafeAreaView style={{ flex: 1, margin: 30 }}>
            {captureVisible ? (
                <>
                    <View style={styles.cameraContainer}>
                        <Camera
                            style={styles.camera}
                            type={type}
                            ref={myCameraRef}
                        >
                            <View style={styles.cameraButtonContainer}>
                                <TouchableOpacity
                                    style={styles.cameraButton}
                                    onPress={toggleCameraType}
                                >
                                    <Text style={styles.text}>flip</Text>
                                </TouchableOpacity>
                                <TouchableOpacity
                                    style={styles.cameraButton}
                                    onPress={() => {
                                        setCaptureVisible(!captureVisible);
                                    }}
                                >
                                    <Text style={styles.text}>out</Text>
                                </TouchableOpacity>
                                <TouchableOpacity
                                    style={styles.cameraButton}
                                    onPress={snapPicture}
                                >
                                    <Text style={styles.text}>snap</Text>
                                </TouchableOpacity>
                            </View>
                        </Camera>
                    </View>
                </>
            ) : (
                <>
                    <HabitPictureSampleContainer />
                    <View style={{ flex: 1 }}>
                        <View
                            style={{
                                alignItems: "center",
                                justifyContent: "flex-start",
                            }}
                        >
                            <TouchableOpacity
                                style={styles.button}
                                onPress={pickImage}
                            >
                                <Text>샘플 사진 가져오기</Text>
                            </TouchableOpacity>
                        </View>
                        <View
                            style={{
                                alignItems: "center",
                                justifyContent: "flex-start",
                            }}
                        >
                            <TouchableOpacity
                                style={styles.button}
                                onPress={() => {
                                    setCaptureVisible(!captureVisible);
                                }}
                            >
                                <Text>샘플 사진 찍기</Text>
                            </TouchableOpacity>
                        </View>
                        <View
                            style={{
                                alignItems: "center",
                                justifyContent: "flex-start",
                            }}
                        >
                            <TouchableOpacity
                                style={styles.button}
                                onPress={() => {
                                    setCaptureVisible(!captureVisible);
                                }}
                            >
                                <Text>사진 인증하기</Text>
                            </TouchableOpacity>
                        </View>
                        <View
                            style={{
                                alignItems: "center",
                                justifyContent: "flex-start",
                            }}
                        >
                            <TouchableOpacity
                                style={styles.button}
                                onPress={() => {
                                    setSamplePictureScreen(
                                        !samplePictureScreen
                                    );
                                    console.log(samplePictureScreen);
                                }}
                            >
                                <Text>샘플 사진 보기</Text>
                            </TouchableOpacity>
                        </View>
                    </View>
                </>
            )}
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    input: {
        height: 30,
        width: 200,
        margin: 12,
        borderWidth: 1,
        padding: 3,
    },
    button: {
        alignItems: "center",
        backgroundColor: "#DDDDDD",
        padding: 10,
        width: 300,
        margin: 16,
    },
    cameraContainer: {
        flex: 1,
        justifyContent: "center",
    },
    camera: {
        flex: 1,
    },
    cameraButtonContainer: {
        flex: 1,
        flexDirection: "row",
        backgroundColor: "transparent",
        margin: 64,
    },
    cameraButton: {
        flex: 1,
        alignSelf: "flex-end",
        alignItems: "center",
    },
    text: {
        fontSize: 24,
        fontWeight: "bold",
        color: "white",
    },
});
export default HabitPictureScreen;
