package kr.ac.mjc.library;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewPager2 viewpager=findViewById(R.id.viewpager);
        TabLayout tab=findViewById(R.id.tab);
        PageAdapter adpater=new PageAdapter(this);
        viewpager.setAdapter(adpater);
        new TabLayoutMediator(tab, viewpager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int i) {
                if(i==0){
                    tab.setIcon(R.drawable.home_24px);
                }
                else if(i==1){
                    tab.setIcon(R.drawable.search_24px);
                }
                else{
                    tab.setIcon(R.drawable.person_24px);
                }
            }
        }).attach();

    }
}