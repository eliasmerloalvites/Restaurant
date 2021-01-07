package corporacion.android.com.restaurant.adaptador;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import corporacion.android.com.restaurant.R;

public class SliderAdapterPresentacion extends PagerAdapter {

    Context context;
    private Boolean border;
    LayoutInflater layoutInflater;

    public SliderAdapterPresentacion(Context context)
    {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layoutpromociones_flotante,container,false);
        ImageView sliderImagen = view.findViewById(R.id.idImagen);
        if (position==0)
        {
            sliderImagen.setBackgroundResource(R.drawable.imagen1);
        }
        if (position==1)
        {
            sliderImagen.setBackgroundResource(R.drawable.imagen2);
        }
        if (position==2)
        {
            sliderImagen.setBackgroundResource(R.drawable.imagen3);
        }
        container.addView(view);

        return  view;
    }
}
