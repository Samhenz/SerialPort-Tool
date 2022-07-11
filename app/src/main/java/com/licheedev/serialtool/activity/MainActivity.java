package com.licheedev.serialtool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.serialport.SerialPortFinder;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.BindView;
import butterknife.OnClick;

import com.licheedev.serialtool.R;
import com.licheedev.serialtool.activity.base.BaseActivity;
import com.licheedev.serialtool.comn.Device;
import com.licheedev.serialtool.comn.SerialPortManager;
import com.licheedev.serialtool.util.AllCapTransformationMethod;
import com.licheedev.serialtool.util.PrefHelper;
import com.licheedev.serialtool.util.ToastUtil;
import com.licheedev.serialtool.util.constant.PreferenceKeys;

import static com.licheedev.serialtool.R.array.baudrates;

import androidx.annotation.NonNull;

public class MainActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinner_devices)
    Spinner mSpinnerDevices;
    @BindView(R.id.spinner_baudrate)
    Spinner mSpinnerBaudrate;
    @BindView(R.id.btn_open_device)
    Button mBtnOpenDevice;
    @BindView(R.id.btn_send_data)
    Button mBtnSendData;
    @BindView(R.id.btn_load_list)
    Button mBtnLoadList;
    @BindView(R.id.et_data)
    EditText mEtData;
    @BindView(R.id.button)
    Button mBtn1;
    @BindView(R.id.button2)
    Button mBtn2;
    @BindView(R.id.button3)
    Button mBtn3;
    @BindView(R.id.button4)
    Button mBtn4;
    @BindView(R.id.button5)
    Button mBtn5;
    @BindView(R.id.button6)
    Button mBtn6;
    @BindView(R.id.button7)
    Button mBtn7;
    @BindView(R.id.button8)
    Button mBtn8;
    @BindView(R.id.button9)
    Button mBtn9;
    @BindView(R.id.button10)
    Button mBtn10;

    private Device mDevice;

    private int mDeviceIndex;
    private int mBaudrateIndex;

    private String[] mDevices;
    private String[] mBaudrates;

    private boolean mOpened = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mEtData.setTransformationMethod(new AllCapTransformationMethod(true));

        initDevice();
        initSpinners();
        updateViewState(mOpened);
    }

    @Override
    protected void onDestroy() {
        SerialPortManager.instance().close();
        super.onDestroy();
    }

    @Override
    protected boolean hasActionBar() {
        return false;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    /**
     * Inicializar lista de dispositivos
     */
    private void initDevice() {

        SerialPortFinder serialPortFinder = new SerialPortFinder();

        // 设备
        mDevices = serialPortFinder.getAllDevicesPath();
        if (mDevices.length == 0) {
            mDevices = new String[] {
                getString(R.string.no_serial_device)
            };
        }
        // 波特率
        mBaudrates = getResources().getStringArray(baudrates);

        mDeviceIndex = PrefHelper.getDefault().getInt(PreferenceKeys.SERIAL_PORT_DEVICES, 0);
        mDeviceIndex = mDeviceIndex >= mDevices.length ? mDevices.length - 1 : mDeviceIndex;
        mBaudrateIndex = PrefHelper.getDefault().getInt(PreferenceKeys.BAUD_RATE, 0);

        mDevice = new Device(mDevices[mDeviceIndex], mBaudrates[mBaudrateIndex]);
    }

    /**
     * Inicializa opciones despegables
     */
    private void initSpinners() {

        ArrayAdapter<String> deviceAdapter =
            new ArrayAdapter<String>(this, R.layout.spinner_default_item, mDevices);
        deviceAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerDevices.setAdapter(deviceAdapter);
        mSpinnerDevices.setOnItemSelectedListener(this);

        ArrayAdapter<String> baudrateAdapter =
            new ArrayAdapter<String>(this, R.layout.spinner_default_item, mBaudrates);
        baudrateAdapter.setDropDownViewResource(R.layout.spinner_item);
        mSpinnerBaudrate.setAdapter(baudrateAdapter);
        mSpinnerBaudrate.setOnItemSelectedListener(this);

        mSpinnerDevices.setSelection(mDeviceIndex);
        mSpinnerBaudrate.setSelection(mBaudrateIndex);
    }

    @OnClick({ R.id.btn_open_device, R.id.btn_send_data, R.id.btn_load_list,
    R.id.button,R.id.button2,R.id.button3,R.id.button4,R.id.button5,
    R.id.button6,R.id.button7,R.id.button8,R.id.button9,R.id.button10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                send1Data();
                break;
            case R.id.button2:
                send2Data();
                break;
            case R.id.button3:
                send3Data();
                break;
            case R.id.button4:
                send4Data();
                break;
            case R.id.button5:
                send5Data();
                break;
            case R.id.button6:
                send6Data();
                break;
            case R.id.button7:
                send7Data();
                break;
            case R.id.button8:
                send8Data();
                break;
            case R.id.button9:
                send9Data();
                break;
            case R.id.button10:
                send10Data();
                break;
            case R.id.btn_open_device:
                switchSerialPort();
                break;
            case R.id.btn_send_data:
                sendData();
                break;
            case R.id.btn_load_list:
                startActivity(new Intent(this, LoadCmdListActivity.class));
                break;


        }
    }

    /**
     *
     */
    private void send1Data() {
        String text = "AAEB22020000B955";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }

    private void send2Data() {
        String text = "AAEB22020001BA55";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }
    private void send3Data() {
        String text = "AAEB22020002BB55";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }
    private void send4Data() {
        String text = "AAEB22020003BC55";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }
    private void send5Data() {
        String text = "AAEB22020004BD55";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }
    //***
    private void send6Data() {
        String text = "AAEB22020005BE55";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }

    private void send7Data() {
        String text = "AAEB22020006BF55";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }
    private void send8Data() {
        String text = "AAEB22020007C055";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }
    private void send9Data() {
        String text = "AAEB22020008C155";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }
    private void send10Data() {
        String text = "AAEB22020009C255";
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }
        SerialPortManager.instance().sendCommand(text);
    }
    /**
     *
     */

    private void sendData() {

        String text = mEtData.getText().toString().toUpperCase().trim();
        if (TextUtils.isEmpty(text) || text.length() % 2 != 0) {
            ToastUtil.showOne(this, "invalid data");
            return;
        }

        SerialPortManager.instance().sendCommand(text);
    }
    /**
     * Abrir o cerrar el serial port
     */
    private void switchSerialPort() {
        if (mOpened) {
            SerialPortManager.instance().close();
            mOpened = false;
        } else {

            // 保存配置
            PrefHelper.getDefault().saveInt(PreferenceKeys.SERIAL_PORT_DEVICES, mDeviceIndex);
            PrefHelper.getDefault().saveInt(PreferenceKeys.BAUD_RATE, mBaudrateIndex);

            mOpened = SerialPortManager.instance().open(mDevice) != null;
            if (mOpened) {
                ToastUtil.showOne(this, "serial port opened");
            } else {
                ToastUtil.showOne(this, "serial port not opened");
            }
        }
        updateViewState(mOpened);
    }

    /**
     * 更新视图状态
     *
     * @param isSerialPortOpened
     */
    private void updateViewState(boolean isSerialPortOpened) {

        int stringRes = isSerialPortOpened ? R.string.close_serial_port : R.string.open_serial_port;

        mBtnOpenDevice.setText(stringRes);

        mSpinnerDevices.setEnabled(!isSerialPortOpened);
        mSpinnerBaudrate.setEnabled(!isSerialPortOpened);
        mBtnSendData.setEnabled(isSerialPortOpened);
        mBtnLoadList.setEnabled(isSerialPortOpened);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // Spinner 选择监听
        switch (parent.getId()) {
            case R.id.spinner_devices:
                mDeviceIndex = position;
                mDevice.setPath(mDevices[mDeviceIndex]);
                break;
            case R.id.spinner_baudrate:
                mBaudrateIndex = position;
                mDevice.setBaudrate(mBaudrates[mBaudrateIndex]);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // 空实现
    }
}
