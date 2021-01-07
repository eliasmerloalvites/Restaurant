package corporacion.android.com.restaurant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import corporacion.android.com.restaurant.adaptador.SliderAdapterPresentacion;

public class Presentacion extends AppCompatActivity {
    private ViewPager mSliderViewPager;
    private SliderAdapterPresentacion sliderAdapter;
    private LinearLayout mLinearLayoutH;
    ArrayList Imagen;
    private TextView[] mText;
    private int mCurrentPage;
    Button btnSiguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentacion);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btnSiguiente = findViewById(R.id.btnSiguiente);
        mSliderViewPager = findViewById(R.id.idViewPagerServicio);
        mLinearLayoutH = findViewById(R.id.idLinearLayourH);
        Imagen = new ArrayList<>();
        sliderAdapter = new SliderAdapterPresentacion(getApplicationContext());
        mSliderViewPager.setAdapter(sliderAdapter);
        addTextLayoutIndicador(0);
        segundo();
        mSliderViewPager.addOnPageChangeListener(viewListener);

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addTextLayoutIndicador(position);
            mCurrentPage = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void segundo()
    {
        CountDownTimer countDownTimer1 = new CountDownTimer(Integer.valueOf(3)*4*1000, 4000) {
            public void onTick(long millisUntilFinished) {
                if (mCurrentPage == mText.length - 1 )
                {
                    mCurrentPage = 0;
                    mSliderViewPager.setCurrentItem(mCurrentPage);
                }
                else
                {
                    mSliderViewPager.setCurrentItem(mCurrentPage + 1);
                }

            }

            public void onFinish() {
                segundo();
            }
        }.start();
    }

    public void addTextLayoutIndicador(int pos)
    {
        mText = new TextView[Integer.valueOf(3) ];
        mLinearLayoutH.removeAllViews();
        for (int i = 0 ; i <mText.length; i++)
        {
            mText[i]= new TextView(this);
            mText[i].setText(Html.fromHtml("&#8226"));
            mText[i].setTextSize(35);
            mText[i].setTextColor(this.getResources().getColor(R.color.colorplomo));

            mLinearLayoutH.addView(mText[i]);
        }
        if (mText.length>0)
        {
            mText[pos].setTextColor(this.getResources().getColor(R.color.colorPrimaryDark));
        }
    }
}