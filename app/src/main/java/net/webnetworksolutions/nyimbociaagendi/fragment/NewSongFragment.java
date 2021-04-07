package net.webnetworksolutions.nyimbociaagendi.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import net.webnetworksolutions.nyimbociaagendi.R;
import net.webnetworksolutions.nyimbociaagendi.adapter.SongBookAdapter;
import net.webnetworksolutions.nyimbociaagendi.other.DividerItemDecoration;
import net.webnetworksolutions.nyimbociaagendi.pojo.Song;

import java.util.ArrayList;

public class NewSongFragment extends Fragment{


    private SearchView sv;
    private  View rootView;
    private ImageView imgNavHeaderBg, imgProfile,imgHeader;

    public static final String TAG = NewSongFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLOR = "color";

    // TODO: Rename and change types of parameters
    private int color;

    private RecyclerView recyclerView;


    public NewSongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            color = getArguments().getInt(ARG_COLOR);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_new_songs, container, false);

    //Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar2);
        //((MainActivity)getActivity()).setSupportActionBar(toolbar);

        imgHeader = (ImageView)rootView.findViewById(R.id.backdrop2);


        sv=(SearchView)rootView.findViewById(R.id.mSearch2);

        //recyclerview and properties
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_square_recycler2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setBackgroundColor(getLighterColor(color));

        //adapter
        final SongBookAdapter adapter = new SongBookAdapter(getContext(),getSongs());
        recyclerView.setAdapter(adapter);

        //search
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //filter as you type
                adapter.getFilter().filter(query);
                return true;
            }
        });

        // initializing toolbar
        initCollapsingToolbar();


        return rootView;
    }


    /**
     * Updates {@link RecyclerView} background color upon changing Bottom Navigation item.
     *
     * @param color to apply to {@link RecyclerView} background.
     */
    public void updateColor(int color) {
        recyclerView.setBackgroundColor(getLighterColor(color));
    }

    /**
     * Facade to return colors at 30% opacity.
     *
     * @param color
     * @return
     */
    private int getLighterColor(int color) {
        return Color.argb(30,
                Color.red(color),
                Color.green(color),
                Color.blue(color)
        );
    }

    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar txtPostTitle on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout)rootView. findViewById(R.id.collapsing_toolbar2);
        collapsingToolbar.setTitle("Ibuku Rieru ");
        AppBarLayout appBarLayout = (AppBarLayout)rootView. findViewById(R.id.appbar2);
        appBarLayout.setExpanded(true);

        // hiding & showing the txtPostTitle when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Nyimbo Cia Agendi");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });

        // loading toolbar header image
        Glide.with(getActivity().getApplicationContext()).load("https://www.facebook.com/photo?fbid=106403524376846&set=a.106403554376843")
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgHeader);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_about:
                openDialog();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openDialog() {

        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        // Set Custom Title
        TextView title = new TextView(getActivity());
        // Title Properties
        title.setText("Please Read!!!");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(getActivity());
        // Message Properties
        msg.setText("Realese Date: Nov/11/2017\n"+
                "Thankyou to God Almighty\n" +
                "Thankyou User\n"+
                " \n Benja Mbuthia");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        new Dialog(getActivity().getApplicationContext());
        alertDialog.show();

        // Set Properties for OK Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.RED);
        cancelBT.setLayoutParams(negBtnLP);
    }

    private ArrayList<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        Song song = new Song();                  //1st song
        song.setTitle("1. No Wee Wi Mutheru");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "No Wee wĩ Mũtheru! Ngai Mũrungu!\n" +
                "Rũciinĩ tene nĩtũgũkũinĩra;\n" +
                "No wee wĩ Mũtheru, Mwathani mũigua tha,\n" +
                "Wĩ Ngai Mũũmbi tene na tene\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "No Wee wĩ Mũtheru! Na araika,\n" +
                "Aingĩ mũno nĩmakũhooyaga othe;\n" +
                "Nĩ ngiri na ngiri cinamagĩrĩra,\n" +
                "Ngai wa tene na hingo ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "No Wee wĩ Mũtheru! ũrĩ hitho-inĩ,\n" +
                "Maitho maitũ matingĩhota gũkuona,\n" +
                "No Wee wĩ Mũtheru! O Wee wiki no We,\n" +
                "Wĩ na ũhoti na wendo wa ma.");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();                                 //2nd song
        song.setTitle("2. Twakugatha Ngai.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Twakũgaatha Ngai, na Mwana na wendo,\n" +
                "Nĩatũkuĩrĩire, na agĩthiĩ igũrũ,\n" +
                "Haleluya! Twakũgatha, Haleluya! Amen\n" +
                "Haleluya! Twakũgaatha, tũraathime rĩu.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Twakũgaatha Ngai, Na Roho Mũtheru,\n" +
                "Nĩatũkũũrire Mwathani Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũgaathe Jesũ, nĩatũkuĩrĩire,\n" +
                "Nĩatũkũũrire agĩtũhonokia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Twakũgatha Ngai, tondũ wa tha ciaku,\n" +
                "Tũhe Roho waku Ngoro-inĩ ciitũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();                              //3rd song
        song.setTitle("3. Ngai Niagoocwo Nĩ Ciumbe Ciake (NZK 3)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngai nĩagoocwo, Nĩ ciũmbe ciake,\n" +
                "Wendo wake nĩwatũheire Jesũ,\n" +
                "ũrĩa werutire atũhonokie,\n" +
                "Tũgĩe na muoyo wa tene na tene.\n" +
                "\n" +
                "\n" +
                "Mũkumie, Mũkumie, Andũ a thĩ maigue,\n" +
                "Mũkumie, Mũkumie, Andũ othe kenai,\n" +
                "ũkaani kwĩ Jesũ Mwana wa Ngai,\n" +
                "Mũmũgooce nĩ ũndũ wa ũũmbi wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "ũhonokio nĩguo, kĩheeo giitũ,\n" +
                "Ngai nĩaatwĩrĩire tũrĩ andũ a thĩ,\n" +
                "Arĩa mamwĩtĩkagia na kuumbũra,\n" +
                "Acio nĩmohagĩrwo meehia mao.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Nĩaatũreheire, ũhoro Mwega;\n" +
                "Agĩthagathaga gũtũhonokia,\n" +
                "No makĩria mũno ũndũ mũnene,\n" +
                "Jesũ nĩagooka na ithuĩ tũmuone.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();                                     //4th song
        song.setTitle("4. Kumiai Riitwa Ria Jesu (NZK 113)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kumiai Rĩĩtwa rĩa Jesũ! Mũinamĩrĩrei,\n" +
                "Inyuĩ mũrĩ igũrũ, kumiai mũmũnenehie,\n" +
                "Inyuĩ mũrĩ igũrũ kumiai, Mũmũnenehie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mũmũnenehiei inyuĩ, Mũrũagĩrĩra Ngai;\n" +
                "Mũmũnenehie Mwathi wanyu, Kumiai! Mũmũnenehie.\n" +
                "Nenehiai Mwathi wanyu, Kumiai! Mũmũnenehie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Inyuĩ rũciaro rũiguĩre tha mũkumiei,\n" +
                "Mũhonoketio nĩ Jesu, kumiai, mũmũnenehie,\n" +
                "Inyuĩ rũciaro rũiguĩre tha mũkumiei mũmũnenehie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Inyuĩ eehia ririkanai mũtharaba,\n" +
                "Mũgoocei mũkenete, Kumiai, mũmũnenehie,\n" +
                "Mũgoocei mũkenete, Kumiai, mũmũnenehie.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();                            //5th song
        song.setTitle("5. Nitumuhooe Jesu Munene.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩtũmũhooe Jesũ Mũnene,\n" +
                "Ngumo ya hinya wake nĩĩhuunjio;\n" +
                "Nĩwe ngo nairahĩro nginya tene,\n" +
                "Nĩ yo ngumo yake tene na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ta rorai thĩ ĩno tũtũire,\n" +
                "ũrĩa yũmbĩtwo na magegania;\n" +
                "Rĩu nĩigĩtwo gĩikaro kĩayo,\n" +
                "O nginya ihiinda rĩayo rĩthire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tondũ worĩa ũtũgitagĩra,\n" +
                "Twacookia ngaatho tondũ wĩ mwega,\n" +
                "Nĩ ũheaga irio cĩũmbe ciothe biũ,\n" +
                "Ciũmbe ciothe nĩ irĩ ciikaro ciacio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ithuĩ andũ tũtirĩ hinya,\n" +
                "Wee, Mwathani no tũkwĩhoke,\n" +
                "ũtugi waku, Ngai ndũrĩ wanyiiha,\n" +
                "Wĩ Mũkũũri witũ mũrata wa ma.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("6. Ruciini Gukiire Wega (NZK 6)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Rũcinĩ gũkĩire wega, Ndahiingũra maitho;\n" +
                "Mwathani nĩamenyereire, Niĩ mwana wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngai wakwa menyerera, Mũthenya ũyũ wothe;\n" +
                "ũnjohere mehia makwa, Njikarage nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Roho waku nĩaikarage, Ngoro-inĩ yakwa;\n" +
                "Nĩaatherie nĩguo nyonage, ũthiũ wake mwega.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("7. Ngai Atuire Atuteithagia (NZK 7)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngai atũire atũteithagia kuuma mĩaka yothe,\n" +
                "Na no We mwĩhoko witũ wa tene na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kĩĩruru gĩa gĩtĩ gĩake nĩ irahĩro riitũ,\n" +
                "Guoko gwaku nĩ kũiganu gwa gũtũgitĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Atanomba thĩ na iria na njata ciothe biũ,\n" +
                "Wee nowe Mũrungu; witũ tũtũũragio nĩ We.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Harĩ Ngai mĩaka ngiri nĩ ta mũthenya ũmwe,\n" +
                "Atũire mĩndĩ na mĩndĩ,ndarĩ kĩambĩrĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Ngai atũire atũteithagia kuuma mĩaka yothe,\n" +
                "Nĩ we Mũgitiri witũ wa tene na tene.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("8. Njigua Ngikaya (NZK 8)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Njigua ngĩkaya, Njiguĩra tha, Ndũhiũhe, Mũkũũri Mũnene,\n" +
                "Ngoro yakwa rĩu nĩwe yetereire, ũka ũka rĩu\n" +
                "Ndũire njũrũraga irĩma-inĩ,\n" +
                "Ndorire ngiuma gwitũ mũcĩĩ\n" +
                "Njoya ũnjokie kiugũ-inĩ giaku kĩega\n" +
                "ũka ũka rĩu\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndaraga nja Ndirĩ nyũmba, Ndũkĩhiũhe, Mũkũũri Mũnene,\n" +
                "Ingĩkuona no njigue ta ndĩ na muoyo, ũka ũka rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ngũigua ndĩ nyiki, O na nganoga, Hiũha, Mũkũũri Mũnene,\n" +
                "Ngwenda gũkuona, Na tũcemanie nawe, ũka ũka rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndũgite ngoro Hehenjeku, Ndũhiũhe, Mũkũũri Mũnene,\n" +
                "Njigua ngĩkaya, ngũkũhoya ũhiuhe, ũka ũka rĩu.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("9. Muthamaki Muumbi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũthamaki mũnyũmbi, mwene indo ciothe\n" +
                "Nĩũndũ wa ũtugi waku, nĩũndathimaga\n" +
                "Nĩũndũ wa ũtugi waku,nĩũndathimaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Wee wanyũmbire no we ndĩhokete\n" +
                "Njagĩrĩirũo nĩgumagie wega waku mũingi\n" +
                "Njagĩrĩrũo nĩ gũkumagie waku mũingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Nĩkĩ ingiruta na ciothe nĩ ciaku\n" +
                "Wee wendaga o tũgũcokagĩrie ngatho\n" +
                "Wee wendaga o tũgũcokagĩrie ngatho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "ũhe wega waku nĩguo ngĩe na hinya\n" +
                "Wa  kũhotithagia nĩguo ndũre nawe Ngai\n" +
                "Wa  kũhotithagia nĩguo ndũrie nawe Ngai. \n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("10. Kristo Muiguaniri Tha (NZK 10)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kristo mũiguanĩri tha ũhe rũĩmbo ngoro-inĩ,\n" +
                "Tondũ wa irathimo ciaku njiyũrũo nĩ gĩkeno.\n" +
                "Ndũtaga nĩguo ngwendage, na ngũrũmagĩrĩre,\n" +
                "Ngoro yakwa ĩiyũrĩte gĩkeno na mwĩhoko.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngũcokeria Ngai ngatho, nĩ ũndũ wa kũhotithia,\n" +
                "Andongoragia na thayũ o nginya mũciĩ gwake,\n" +
                "Rĩrĩa Jesũ anjaragia ndaarĩ kũraya nake,\n" +
                "Agĩita thakame yake andute ũgwati-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndĩ na thiirĩ mũnene ma tondũ wa kũiguĩrwo tha,\n" +
                "Wendani waku ũnguucie njikarage hamwe nawe.\n" +
                "Nĩguo ndikaguucio rĩngĩ ngũtige we Mũhonia,\n" +
                "Oya ngoro yakwa rĩu ĩtuĩke yaku kũna.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("11. Jesu Uugite Eri Kana Atatu (NZK 13)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                " Jesũ uugĩte erĩ kana atatũ\n" +
                "Mongana magwĩte no ũkamaigua,\n" +
                "Nĩtũgwĩtĩkie We, Nĩtwakũhoya,\n" +
                "ũũka Jesũ rĩũ ũtũrathime.\n" +
                "\n" +
                "\n" +
                "ũka ũtũrathime, Nĩtũgwetereire,\n" +
                "Ndũgĩũke ũtũrathime, Tũkuhĩrĩrie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.  \n" +
                "Jesũ nĩũcemanagia hamwe na ithui,\n" +
                "O na rĩu ũka ũtũceerere\n" +
                "Tondũ wĩ mũrathime ũtwamũkĩre,\n" +
                "Tũhe wega waku nĩtwagũthaitha.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.  \n" +
                "Jesũ tũhe mĩgambo ĩrĩa mĩega\n" +
                "Tũkũinĩre nayo tũgũthathaiye,\n" +
                "Tũhe wĩtĩkio mũingi O na mwĩhoko\n" +
                "Tũhe wendo waku ũrĩa mũtheru.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("12. Migambo Yothe Nĩ iinire(NZK 11)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mĩgambo yothe ta nĩ ĩĩnĩre, Rĩĩtwa rĩa Jesũ Mwathani!\n" +
                "Mũthamakĩ na nĩngĩ no Ngai, Rĩĩtwa rĩake rĩrĩ ngumo!\n" +
                "\n" +
                "\n" +
                "Rĩĩtwa rĩĩ ngumo ,rĩĩtwa rĩĩ ngũmo\n" +
                "Rĩĩtwa rĩa Jesũ Mwathani,\n" +
                "Rĩĩtwa rĩĩ ngumo, rĩĩtwa rĩĩ ngumo,\n" +
                "Rĩĩtwa rĩa Jesũ rĩĩ ngumo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Rĩniinaga gũoya wa ngoro-inĩ, Rĩĩwa rĩa Jesũ rĩĩ ngũmo,\n" +
                "O na mwĩhia noarĩtĩkagia, Rĩĩtwa rĩa Jesũ rĩĩ ngũmo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Rĩniinaga guoya wa mehia, Rĩĩtwa rĩa Jesũ \n" +
                "rĩĩ ngumo,\n" +
                "Atheranagia ki na thakame, Rĩĩtwa rĩa Jesũ rĩĩ ngumo.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("13. Gathai Ngai Andu Aya");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                " Gaathai Ngai, Andũ aya;\n" +
                "Inyuĩ thĩ, na araika.\n" +
                "Nĩ we Mwega, nĩagaathwo,\n" +
                "Ithe, Mũriũ, o na Roho, Amen\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("14. Reke Njerage Nawe Ngai(NZK 14)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Reke Njerage nawe Ngai, o ta Enoko wa tene,\n" +
                "ũnyite na guoko gwaku, ningĩ tũteretage nawe,\n" +
                "O Na ndaga kuona njĩra, Jesũ ndĩrĩceraga nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndihota gwĩtwara nyiki, tondũ wa kĩhuhũkanio,\n" +
                "O na marima nĩ maingĩ, Thũ nacio nĩ nyingĩ mũno,\n" +
                "Horeria kĩhuhũkanio, Jesũ ndĩrĩceraga nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndanyita guoko kũu gwaku, nĩngũtiga ikeno cia thĩĩ\n" +
                "Thiĩ na mbere rũgendo-inĩ, bendera nĩ mũtharaba,\n" +
                "O kinya nginye sayuni, Jesũ ndĩrĩceraga nawe.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("15. Njariria Mwathani Jesu (NZK 15)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Njarĩria Mwathi Jesũ, Njarĩria na wendo,\n" +
                "Aria na niĩ ũnjĩĩre, ”Ndũgũtigwo wiki”\n" +
                "Hingũra ngoro yakwa, nĩguo ngũigue narua,\n" +
                "ũnjiyũrie ũgooci, nĩguo ngũgoocage.\n" +
                "\n" +
                "\n" +
                "Njarĩria hingo ciothe, Njarĩria na wendo,\n" +
                "ũnjarĩrie kahora, njarĩria ũnjĩre,\n" +
                "“Nĩũrĩhootanaga, wĩathi nĩ waku”\n" +
                "O na ningĩ ũnjĩĩre: “Ndũgũtigwo wiki”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Arĩria andũ aku, na ũmoonie njĩra,\n" +
                "ũmaiyũrie gĩkeno, marute kũhooya;\n" +
                "Marute kwĩheana, kwĩheana kũna,\n" +
                "ũmonie ũthamaki, tũone Mũhonokia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Njarĩria o ta tene, Rirĩa warutire,\n" +
                "Maathani marĩa ikũmi, Hotithia kũmaigua,\n" +
                "Nĩ geetha Ngai wakwa, Nduĩke waku kũna,\n" +
                "Hotithagio nĩ wendo, Nĩguo ngũkumagie.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("16. Jesu Nimbatairio Niwe (NZK 16)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩmbatairio nĩwe, Ndĩ kĩonje ndĩ mũthĩnĩ,\n" +
                "Nyita guoko ũndongorĩe, Na ũhĩngũre maĩtho\n" +
                "\n" +
                "\n" +
                "O hingo, O hingo, Jesũ nĩmbataĩrĩo nĩwe,\n" +
                "O hingo, O hingo, Mwathanĩ O ndongoragie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Huumba na ũhorerĩ waku, Ndihumbĩte o mehia,\n" +
                "Na ũnyonie wonje wakwa, Na ũnyonĩe kũhoya\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Wandoongorĩa ndĩĩtigĩra, No ndĩ nyiki nĩngũgwa;\n" +
                "Ngwenda tũtwaranage nawe, ũmũrĩkĩre njĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Namo marĩa mangĩndũnga, Mĩtheko kana kĩeha,\n" +
                "Ndĩrĩĩhocagĩa harĩ we, Ngaĩgũa gĩkeno ngoro.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("17. Ti Nii No Kristo (NZK 17)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ti niĩ no Kristo nowe wa kũgoocwo;\n" +
                "Ti niĩ no Kristo amenywo mũno,\n" +
                "Ti niĩ no Kristo thiĩnĩ wa mĩaro;\n" +
                "Ti niĩ no Kristo gĩĩko-inĩ gĩothe,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ti niĩ, no Kristo mũhonania wĩ tha;\n" +
                "No Kristo tu ũgiragia maithori,\n" +
                "Ti niĩ no Kristo ũngĩhũthia maũndũ,\n" +
                "Ti niĩ no Kristo mũnini guoya.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "No Kristo tu, ũtangĩgĩa mwĩgaatho,\n" +
                "No Kristo tu wa kũnjarĩrĩria;\n" +
                "No Kristo tu kũngiaga mwĩtĩĩo\n" +
                "No Kristo tu kwĩgooca gũkaaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "No Kristo tu, ũhiingagia mabata;\n" +
                "Ti niĩ no Kristo, gĩthima gĩakwa;\n" +
                "No Kristo tu, Mwĩrĩ na ngoro-inĩ,\n" +
                "Ti niĩ no Kristo tene ne tene\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("18. Tutongorie O Ta Muriithi (NZK 18)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Tũtongorie o ta Mũrĩithi, Jesũ tũmenyerere.\n" +
                "Tũtware nyeki-inĩ, nĩguo tũgũkenagie.\n" +
                "Jesũ witũ, Jesũ witũ, Nĩwe watũgũrire,\n" +
                "Jesũ witũ, Jesũ witũ, Nĩwe watũgũrire,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.  \n" +
                "Rĩu tuĩka mũrata witũ, Na ũtũtongoragie,\n" +
                "Tũgirie gũcoka kwĩhia, twora ũtũũcaragie,\n" +
                "Jesũ witũ, Jesũ witũ, Rĩu tũthikĩrĩrie,\n" +
                "Jesũ witũ, Jesũ witũ, Rĩu tũthikĩrĩrie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Nĩugĩte tũũke kũrĩ we, O na tũrĩĩ ehia mũno,\n" +
                "Tha ciaku nĩigũtuohora nga, Tũtherie twamũkĩre.\n" +
                "Jesũ witũ, Jesũ witũ, Nĩtwagũcokerera,\n" +
                "Jesũ witũ, Jesũ witũ, Nĩtwagũcokerera.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("19. Mutharaba-ini wa Mwathani.(NZK 19)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũtharaba-inĩ wa Mwathani nĩho ndahoire ũhonokio\n" +
                "Ngoro yakwa nĩyatheririo Jesũ nĩ agoocwo\n" +
                "\n" +
                "\t\n" +
                "Jesũ nĩagoocwo Jesũ nĩagoocwo\n" +
                "Nĩangũũrire na thakame Jesũ nĩgoocwo\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Hau matu-inĩ ũcio wa thĩĩna nĩho ndahoire ũthingu \n" +
                "Nĩaangũũrire na thakame, Jesũ nĩagoocwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Nĩndahonokirio nĩ Jesũ, rĩu aikaraga ngoro-inĩ\n" +
                "Nĩangũrire mũtharaba-inĩ, Jesũ nĩagocowo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Thakame ya Jesũ ya goro, nĩyo ĩndutaga meehia-inĩ\n" +
                "ĩndwaraga ũhonokio-inĩ Jesũ nĩagoocwo.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("20. Ngai Witu Ni Ta Ruaro (NZK 20)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngai witũ nĩ ta rũaro, na irathĩro kwagĩa thĩna,\n" +
                "ũngĩhitha thĩinĩ wake, Ndũrĩ hingo ũgatorio.\n" +
                "Nĩ rũaro rũa gũtũhonokia, kĩĩruru handũ ha kwĩyũa\n" +
                "Atũtongoria rũgendo-inĩ, nĩtũrĩtooragia ũũru.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩ kĩĩruru kwĩ mũthenya, na gwatuka nĩ rũgiri,\n" +
                "Ndũrĩ ũũru ũgetigĩra, wĩhithĩte thĩinĩ wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Kĩguũ kĩnene kĩa ũũru, kĩahota kũrigicĩria,\n" +
                "No twehitha thĩini wake, Gũtirĩ ũũru tũkona\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("21. Ukaani Tukumie Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "ũkaani tũkumie Jesũ,\n" +
                "Nĩwe waatũhonokirie,\n" +
                "Nĩtwĩheane kũrĩ we,\n" +
                "Na indo ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Rĩĩtwa rĩa Jesũ nĩrĩega,\n" +
                "Na norĩo rĩtũkenagia,\n" +
                "Tondũ nĩagatũhootithia,\n" +
                "Kũrũa mbaara.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mwathani tũmenyerere.\n" +
                "ũtũteithie kũiguana,\n" +
                "Nĩguo tũgaatũra nawe,\n" +
                "Kũu igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Kwoguo twathiĩ kũu igũrũ\n" +
                "Tũkahanana na Jesũ,\n" +
                "Natũkagaĩrũo muoyo,\n" +
                "ũtagaathira.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("22. Ndukandige Muhonokia (NZK 22)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũhonokia, riu thikĩrĩria,\n" +
                "ũgĩũka gũcerera arĩa angĩ, ndũkahĩtũke.\n" +
                "\n" +
                "\n" +
                "Jesũ Jesũ, njigua Mwathani\n" +
                "ũgĩũka gũcerera  arĩa angĩ, ndũkahĩtũke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Gĩtĩ gĩaku kĩrĩa gĩa tha, nĩkĩo ndorete,\n" +
                "Ndurĩtie ndu o gĩtĩ-inĩ, nĩguo njoherũo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Ndĩrĩ ũngĩ ingĩĩhoka, tiga we wiki,\n" +
                "Njerekeria ũthiũ waku, nĩngũkũhoya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "No we wiki Mũhoreria, ndirĩ na ũngĩ,\n" +
                "Igũrũ na thĩ ĩno guothe, ũgũkĩrĩte.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("23. Ningwendete Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Nĩngwendete Jesũ, Nĩngwendete ma,\n" +
                "Nĩngwendete Jesũ na Ngai wakwa.\n" +
                "Nĩngwendete mũno, nawe nĩũũĩ,\n" +
                "Nacio ciĩko ciakwa nĩciumbũraga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ngenaga na ngoro, na ndigĩtiga,\n" +
                "Nĩ ũtonga njĩrĩirũo ngenda ndĩwone.\n" +
                "Ngaikara na Jesũ, na araika,\n" +
                "Na andũ ngiri nyingĩ arĩa mendaine.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Ndaikara na Jesũ no ngarathimwo,\n" +
                "No ngona gĩkeno na kĩhurũko,\n" +
                "ũhonokio waku na wendo waku,\n" +
                "Nĩcio ndĩinagĩra na kanua gakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                " We Mũkũũri wakwa, Wĩ Mũthamaki,\n" +
                "Wa salemu njega na ũndeithagia,\n" +
                "Ngũgũkumia na mũgambo wa igũrũ,\n" +
                "Ngũkenera Jesũ rũĩ rũa muoyo.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("24. Jesu Mukuuri Wakwa");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Jesũ Mũkũũri wakwa, we gĩkeno gĩakwa,\n" +
                "Nowe ũhoragĩria, Rĩrĩa ndagĩa na kĩeha.\n" +
                "\n" +
                "\n" +
                "Jesũ Mũkũũri wakwa, Nowe ndĩĩnagĩra,\n" +
                "Ndirĩ na mũrata ũngĩ, ũngĩnyenda o tawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Nowe mwĩhoko wakwa, mĩaka yakwa yothe,\n" +
                "Ndanoga na ndarĩra Nĩũrĩhoragĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "We nowe ndĩhokete ũndongorie wega,\n" +
                "We nowe mwendwa wakwa Ndũhana arata arĩa angĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Rĩrĩa ngũina ngenete Gũkũ thĩ ya mehia,\n" +
                "Nĩ tondũ njetereire Gũtonya kũu Igũrũ\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("25. Jesu Gikeno Kia Ngoro (NZK 23)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ gĩkeno kĩa ngoro! Mũthithũ wa wendo, na ũtheri\n" +
                "Marĩa tũkenagio nĩmo, matingĩigananio nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Wee wĩ wama hingo ciothe, nĩũcookagĩria arĩa maagwĩta;\n" +
                "Na hingo Ciothe we mwega kũrĩ arĩa magũcaragia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Wee wĩ mũgate wa muoyo, kũwamũkĩra nĩ kĩraathimo.\n" +
                "Ngoro nĩĩcanjamũkaga twanyua Gĩthima-inĩ gĩaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩũtũbatairie Mũkũũri, harĩ wee ngoro nĩihurũkaga,\n" +
                "Twakũrũmia na wĩtĩkio, Wee nĩũrĩtũraathimaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Jesũ nĩwe ũtheri witũ, tũkenagie hingo ciothe,\n" +
                "Ingata nduma ya meehia, ũtuĩke ũtheri wa muoyo.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("26. Niekiruo Thuumbi (NZK 25)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩekĩrũo Thũũmbĩ, Thũũmbĩ nyingĩ mũno,\n" +
                "Gatũrũme ge gĩtĩ-inĩ, Mũnene wa anene;\n" +
                "Na niĩ ndĩmũgooce, Nĩũndũ wa kũngũũra,\n" +
                "Nĩ Mũthamaki mũgate, Mũnene wa igũrũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Nĩekĩrũo Thũũmbĩ, Mwana wa Mũirĩtu,\n" +
                "Aciĩkĩrĩte mũtwe, Nĩetahĩre itaha;\n" +
                "Mũnene wa anene, Mũrĩithi wa andũ,\n" +
                "Gĩtina mũri ta wa Jesii, wa Bethlehemu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Nĩekĩrũo Thũũmbĩ, Mwathani wa wendo;\n" +
                "Irema ciake nĩ ũrirũ, Arĩ gĩtĩ-inĩ,\n" +
                "Gũtirĩ mũraika, kũũria igũrũ,\n" +
                "ũngĩhota gũciona na ndagege mũno ma!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Nĩekĩrũo Thũũmbĩ, Mwathani wa Thayũ\n" +
                "Gũũkũ thĩ guothe mbaara, nĩĩgaathira biũ;\n" +
                "Naguo ũthamaki, nĩũgathiĩ na mbere\n" +
                "Na nĩtũgaakũgooca, tene ona tene.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("27. Ni Ka Na Ku Gakeenge?");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Nĩ ka na kũ gakenge,\n" +
                "Kaige nyũũmba ya ng’ombe?\n" +
                "\n" +
                "\n" +
                "Kaĩ ndeto nĩ Mwaathani\n" +
                "Nĩwe Mũthamaki witũ.\n" +
                "Nĩtũgwe thĩ harĩ we,\n" +
                "Tondũ nĩwe Mũnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Mwanake nake nũũ,\n" +
                "ũgwete wĩra wake?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "ũrĩa mũgerie mũno,\n" +
                "E mũhũũtu nake nũũ?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Nũũ ũyũ ũkũrĩra,\n" +
                "Mũraata wake aakua?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5. \n" +
                "Mũgũũnda-inĩ ũtukũ\n" +
                "ũrĩa wĩna kĩeha nũũ?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t6. \n" +
                "ũyũ nũũ ũrathĩĩnio,\n" +
                "Mwambe mũtĩ igũrũ?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t7. \n" +
                "Na ũrĩa wariũka nũũ\n" +
                "Atooretie gĩkuũ?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t8. \n" +
                "Rĩu Mũnene nĩ ũrĩkũ,\n" +
                "Wathanaga igũrũ?\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("28. Ninagainaga Uhoro Mwega (NZK 11)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Nĩngainaga ũhoro mwega, Wa Jesũ ũrĩa wanguĩrĩire;\n" +
                "Agĩtiga ũnene wake, Agĩkua mũtĩ igũrũ.\n" +
                "\n" +
                "\n" +
                "Nĩngamũinĩra, nĩngamũinĩra, Hamwe na andũ atheru;\n" +
                "Nĩngamũinĩra, nĩngamũinĩra, Kĩũngano-inĩ kĩnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Hĩndĩ ĩrĩa niĩ ndorĩĩte, Mwathani nĩanyonire,\n" +
                "Akĩhĩĩmbĩria na mooko, akĩnjokia harĩ we.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Hĩndĩ ĩrĩa ndatihaangĩtio, na nganogio nĩ meehia;\n" +
                "Mwathani nĩaahonirie, Akĩninĩra guoya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "O na nduma ĩngĩtumana, Ngere njĩra cia kĩeha;\n" +
                "Mũhonokia e hamwe na niĩ, Nĩekũndoongoria wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5. \n" +
                "Hĩndĩ ĩrĩa ndĩ na magerio, We nĩanyũmagĩrĩria;\n" +
                "Nĩ geetha tũgaacemania, Na araata ake othe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("29. Ukaani Andu Aria");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "ũkaani andũ arĩa, twendeete Mwathani,\n" +
                "Tũkahuunjie ũhoro, na ũhoti wake,\n" +
                "Tũhuunjĩrie andũ othe, Tũhunjĩrie andũ othe,\n" +
                "Tũrĩhe thiirĩ wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Jesũ nĩaatigire ũnene igũrũ,\n" +
                "Agĩũka gũũkũ thĩ, nĩũndũ wa gũtwenda,\n" +
                "Ooragirũo nĩũndũ witũ, Ooragirũo nĩũndũ witũ,\n" +
                "Nĩũndũ wa mahĩtia maitũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ nĩaariũkire, Akiuma kũrĩ akuũ,\n" +
                "Akĩhoota gĩkuũ, Agĩgĩtooria biũ,\n" +
                "Akĩambata gwake igũrũ, Akĩambata gwake igũrũ,\n" +
                "Jesũ Kristo nĩwe Ngai.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("30. Riitwa Ria Jesu Ni Riega (NZK 24)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Rĩĩtwa rĩa Jesũ nĩ rĩega, Rĩrĩa tũkũrĩigua,\n" +
                "Rĩhonagia ngoro ciitũ, Na rĩgatũigana.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngoro ĩrĩa ĩtihangĩtio, Nĩĩhonagio nĩrĩo,\n" +
                "Rĩhũũnagia ngoro hũũtu, Rĩkamĩhooreria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Rĩĩtwa rĩu nĩ gĩtina, Nĩ ngo, na rĩũrĩro,\n" +
                "Nĩ mũthiithũ na ũtoonga, Na indo ciakwa ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Jesũ nĩwe Mũrutani, Mũraata, Mũriithi,\n" +
                "Kĩambĩrĩria, kĩrĩĩkĩro, Na indo ciakwa ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Ndingĩhota gũgũkumia, ũrĩa kwagĩrĩire,\n" +
                "No ũgooci ũyũ wakwa, Jesũ wĩtĩkĩre.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("31.  Jesu Muukuri Ruaro Rurumu (NZK 40)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ Mũkũũri Rũaro rũrũmu,\n" +
                "Mathĩĩna-inĩ ndirĩ na kĩeha,\n" +
                "Hau rũaro-inĩ nĩangitagĩra,\n" +
                "Na ndirĩ hĩndĩ ngetigĩra.\n" +
                "\n" +
                "\n" +
                "Mwathi Jesũ Rũaro rũrũmu ma,\n" +
                "Na mũraata na Mũhonokia,\n" +
                "Andoongoragia o kũũndũ guothe,\n" +
                "Na thĩ-inĩ wake kwĩ na thaayũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Angitagĩra hingo ya thĩĩna,\n" +
                "Rũaro rũrũmu, Arokumio,\n" +
                "Nĩahooreragia o na mathĩĩna,\n" +
                "Rĩrĩa e ho ndingĩĩtigĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũrĩ na Jesũ nĩngũmahoota,\n" +
                "Magerio, mothe na mathĩĩna,\n" +
                "Ndĩ na gĩkeno thĩini wa Jesũ,\n" +
                "O na ũhootani hĩndĩ ciothe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("32. Mbuku Ya Ngai");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.  \n" +
                "Mbuku ya Ngai ĩtwĩra atĩa?\n" +
                "Wĩyũrie naniĩ ndĩyũrie;\n" +
                "Tũhĩtithĩtio nĩ tũnua twa andũ \n" +
                "Mbuku theru yugete atĩa?\n" +
                "\n"+
                "Mbuku ya Ngai yugĩte atĩa?\n" +
                "Mbuku ya Ngai mĩthomage;\n" +
                "Igua maathani,mathaani ikũmi\n" +
                "Weterere Jesũ Kristo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "No andũ  anyinyi marũmbũyagia\n" +
                "Maathani macio ikũmi;\n" +
                "Rĩa kana nĩ ite ona rĩa kerĩ\n" +
                "Makagayania rĩa ikũmi\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Niatĩa mũkoiga mwathani acoka \n" +
                "Inyui mũgarũranagia;\n" +
                "Mũkamwĩra atĩa mũtigakane\n" +
                "Mwĩcũraniei o rĩu.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("33. Turaatime Tugithii (NZK 33)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũraathime tũkĩinũka Na ũtũme tũkenage;\n" +
                "Na tũgĩe na wendo waku, Wega na gũtoorania,\n" +
                "Na ũtũkenagie ma, Twĩ rũgeendo-inĩ thĩ ĩno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Twagũkumia twĩ na ngaatho, Nĩ ũndũ Wĩ Roho Mwega,\n" +
                "Maciaro ma ũhonokio, Tũgĩe na mo ithuothe,\n" +
                "Tũrũũgame hingo ciothe, Kiugo-inĩ kĩa Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Matukũ maitũ maingĩha, Tũmanengere Jesũ,\n" +
                "Tũgĩe na hinya ngoro-inĩ, Tũtikae kũnoga;\n" +
                "Nginya hĩndĩ ĩrĩa tũkoona, Riiri wa Jesũ witũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("34. O Ringi Jesu Nitwarikumia (NZK 27)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "O rĩĩngĩ, Jesũ, nĩtwarĩkumia,\n" +
                "Rĩĩtwa rĩu rĩaku rĩrĩ ngumo,\n" +
                "Twetereire ciugo ciaku njega,\n" +
                "Tũtanainũka na gwitũ mũciĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Tũhe thaayũ rũgeendo-inĩ rwitũ,\n" +
                "Tondũ wee ni ũteithio witũ,\n" +
                "Tũrigiririe tũtikahitie,\n" +
                "Na tũnua tũũtũ tũkũinagira.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "ũtũhe thaayũ ũtukũ ũyũ,\n" +
                "Niguo nduma ituike ũtheri.\n" +
                "ũtũmenyerere Mũhonokia,\n" +
                "Mũthenya o ũndũ ũmwe na ũtukũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                " ũtũhe thaayũ riria tũri thi,\n" +
                "Thaayũ ni mũthaiga twi na kieha;\n" +
                "Riria ũgaatwita tũũke gwaku,\n" +
                "ũtũhe thaayũ tene na tene.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("35. Oya Riitwa Riu Ria Jesu (NZK 28)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Oya rĩtwa rĩu rĩa Jesũ, we ũnyamarĩkĩte,\n" +
                "Nĩrĩrĩkũhoreragia, Thiĩ narĩo kũndũ guothe.\n" +
                "Nĩ rĩega, Mũno ma, matu-inĩ na gũkũ thĩ,\n" +
                "Nĩ rĩega, kũndũ guothe, matu-inĩ na gũkũ thĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Oya rĩtwa rĩu rĩa Jesũ, Rĩtuĩkage ngo yaku,\n" +
                "Magerio mangĩgũthĩnia, ũmũhoyage narĩo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Rĩĩtwa rĩa Jesũ nĩ rĩega, Rĩtũkenagia ngoro,\n" +
                "Atũhĩmbĩrie na moko, na ithuĩ tũmũinagĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Rĩrĩa twamũinamĩrĩra, Tũkegũithia harĩ we,\n" +
                "Mũthamaki wa Athamaki, nĩguo tũrĩmwĩtaga.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("36. Jesu Ningwendetee (NZK 29)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩngwendete wee ũtoonga wakwa\n" +
                "Ikeeno cia mehia nĩihutatĩire\n" +
                "Nĩ Mwathani Jesũ wahonokirie \n" +
                "Rĩu nĩngwendeete makĩria mũno\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Ngoro ĩiyũrĩte wendo mũnene\n" +
                "Tondũ nĩwaambire kũũnyenda mbere\n" +
                "ũkĩndutĩra muoyo waku we mwene\n" +
                "Rĩu nĩngwendeete….makĩria mũno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Hĩndĩ ĩrĩa waambirwo mũtharaba-inĩ\n" +
                "Nĩgetha twoherwo tume mehia-inĩ\n" +
                "Na nĩ wekĩrirwo thũũmbĩ ya mĩigua\n" +
                "Rĩu nĩngwendeete….makĩria mũno\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4\n" +
                ".Bũrũri mũthaka na itũũro njega\n" +
                "Rĩrĩa ngaciĩrorera kũrĩa igũrũ\n" +
                "Ngaaria ndĩ na thũmbĩ ĩrĩa ngekĩra\n" +
                "Rĩu nĩngwendeete makĩria mũno.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("37. Jesu Niunyendeete (NZK 30)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Jesũ nĩũnyendete, Nĩngũteng’era gwaku\n" +
                "Nĩwe ũnjiganĩte ũrĩa mũũru angĩũka.\n" +
                "Mbaru-inĩ ciaku Jesũ ũhithe muoyo wakwa,\n" +
                "ũhithe Mũhonokia, Honokia ngoro yakwa.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ndirĩ hangĩ ngwĩhitha, we nowe ndĩĩhokete\n" +
                "Ndũkandige Mwathani, ũnjiguĩre tha Jesũ,\n" +
                "Niĩ nĩngwĩtĩkĩtie, Nĩwe ũhotithagia,\n" +
                "Rĩrĩa ndona ũgwati, mbaara-inĩ tũrĩ o nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Nĩmbataragio nĩwe, nĩũmenyagĩrĩra\n" +
                "Rĩrĩa njagĩte kĩndũ, We nĩwe ũndeithagia\n" +
                "Teithia arĩa maragwa, Cionje ũcihe hinya\n" +
                "Honia arĩa marũarĩte, Tongoria atumumu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                " Thakame ĩo yaku ĩtheretie  wega ma\n" +
                "Nũiyũrĩwo nĩtha, nacio igatũigana\n" +
                "Gwaku Mwathani nĩkuo, Gwĩgĩthima kĩa muoyo,\n" +
                "Therũka ngoro-inĩ yakwa tene na tene.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("38. Nguinira Wendo Wake (NZK 31)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\tt1.\n" +
                "Ngũinĩra (nguinĩra) wendo wake,\n" +
                "Wendo wa (Weendo wa) Jesũ wakwa,\n" +
                "Atũire (atũire) anyendeete,\n" +
                "Nginya gũkua Kalwarĩ.\n" +
                "\n" +
                "\n" +
                "Ngũinĩra wendo wake;\n" +
                " Ngũkumia wendo wake.\n" +
                "Akuire nyone muoyo,\n" +
                " Ngũinĩra wendo wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩarĩri (niarĩri) re maithori,\n" +
                " Na ndirĩ (na ndirĩ) ndĩrarĩra;\n" +
                "O na kuo (o na kuo)gũthaithana,\n" +
                "Nĩathaithanagĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Igũrũ (iguru) na thĩ hihi!\n" +
                "Mwamenya (mwamenya) mehia makwa?\n" +
                "Mooru ma (mooru ma), Nowe Jesũ, \n" +
                "Nĩathambĩtie mothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndirĩ nda (ndirĩ nda) gwĩka wega, nawe no (nawe no) ũnyendete,\n" +
                "Njoya na (njoya na) ũndũngorie, \n" +
                "ũndeithie ngwendage.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("39. Ndi Ruimbo Nyendeete Kuina (NZK 32)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.  \n" +
                "Ndi rũimbo nyendete kũina, Kuuma ndarutwo mehia-inĩ,\n" +
                "Nyinagĩra Mũkũũri wakwa Na Mũthamaki wakwa\n" +
                "\n" +
                "\n" +
                "Tondũ nĩngũũrĩtwo, Tondũ nĩngũũrĩtwo,\n" +
                "Ngũgoca rĩĩtwa rĩake, Tondũ nĩngũũrĩtwo\n" +
                "Ngũgoca rĩĩtwa rĩũ rĩake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Kristo nĩanjĩganĩte kuuma ndarutwo mehia-inĩ,\n" +
                "Ndĩmũtungatage na kũmũigua Tondũ nĩangũũrĩte.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Ndĩ mũira ũtarĩ nganja kuuma ndarutwo mehia-inĩ,\n" +
                "Ndirĩ guoya kana nganja Tondũ niĩ nĩangũũrĩte.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("40. Niakuire Muti-ini");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩakuire mũtĩ-inĩ, Jesũ Mũngũũri,\n" +
                "Atũhonokie, Mwathi witũ.\n" +
                "\n" +
                "\n" +
                "Na nĩ ariũkire, Na akiuma mbĩrĩra yake;\n" +
                "Nĩaariũkire na akiuma mbĩrĩra,\n" +
                "Na rĩu nĩatũire kũũria igũrũ;\n" +
                "Ee muoyo, Ee muoyo Mwathi witũ ee muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Makĩmũthika ho, Jesũ Mũngũũri;\n" +
                "Thĩĩnĩ wa nguruunga, Mwathi witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mũthenya wa ĩtatũ, Jesũ Mũngũũri;\n" +
                "Akiuma ngurunga Mwathi witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Thigari ciarĩ ho, Jesũ Mũngũũri;\n" +
                "Ndakanahonoke, Mwathi witũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("41. O Ruciini Nituonaga (NZK 90)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "O rũciinĩ nĩtuonaga, Maũndũ meerũ ma wendo,\n" +
                "Tũgitagĩrwo nduma-inĩ, Kinya rũciinĩ twokĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Gwakĩa maũndũ no meerũ, Tha, wega, o na ũgima,\n" +
                "ũhonokio, na kuoherũo, Meciiria mega, gĩkeno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũngĩĩrutanĩria rĩu, gũthĩĩna mũthiĩre mwega,\n" +
                "Ngai nĩarĩtuonagia, marĩa marĩmũkenagia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Maũndũ maitũ ma thĩ ĩno, Ngai nĩakamerũhagia,\n" +
                "Akagarũra mathĩĩna, matuĩkage kĩraathimo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Marĩa tũngĩĩkĩra Ngai, O na me manyinyi mũno,\n" +
                "Nĩmaiganu harĩ Ngai; Nĩmagatũteithagia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t6.\t\n" +
                "Wee Ngai hĩndĩ ciothe, tũteithagie maũndũ-inĩ,\n" +
                "Mĩthiĩre iitũ ĩtuĩke; ũrĩa tũrĩkũhooyaga.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("42. Uhoro Wa Muoyo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "ũhoro wa muoyo, Nĩ wa Mwathani Jesũ,\n" +
                "Ningĩ nĩ wa hinya ma, gũtũtoongoragia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na maũndũ mooru, mangĩtũkuhĩrĩria,\n" +
                "Jesũ nĩwe mũhoti, Wa gũtũhonokia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Hĩndĩ ya magerio, Nĩwe mũtũteithia,\n" +
                "Tũngĩmũkaĩra ma, nĩegũtũhonokia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mũndũ ũmwĩhokete, ndangĩaga gũkena,\n" +
                "Tondũ ũtheri wake, arĩ naguo mwene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "ũtoonga wa thĩ ĩno, Ndũngĩtũnyamarũra,\n" +
                "No thaayũ wa Mwathani, ndũrĩ na mũhaka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t6.\t\n" +
                "Matukũ maathira, Magũtũũra gũũkũ thĩ,\n" +
                "Tũgatũũra na Jesũ, tene ona tene.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("43. Njira Uhoro Wa Jesu (NZK 34)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Njĩĩra ũhoro wa Jesũ, wandĩke ngoro yakwa,\n" +
                "Njĩĩra ũhoro ũcio mwega Gũkĩra ng’ano ciothe,\n" +
                "Njĩĩra ũria araika, maamũinĩre aciarũo,\n" +
                "Ngai nĩakumio Igũrũ, Thĩ o nakuo kwendanwo.\n" +
                "\n" +
                "\n" +
                "Njĩira ũhoro wa Jesũ, Wandĩke ngoro yakwa,\n" +
                "Njĩira ũhoro ũcio mwega, Gũkĩra ng’ano ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Werũ-inĩ ndarĩire kĩndũ njĩĩra ũrĩa gwatariĩ,\n" +
                "Na tondũ wa mehia maitũ, aagerio akĩhootana.\n" +
                "Njĩĩra wĩra wake  wothe wa kĩeha kĩnene ma,\n" +
                "Kũmenwo o na gũthĩnio, akĩregwo ta mũthĩni.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Hau mũtharaba-inĩ nĩaheirũo ruo rũingĩ,\n" +
                "Njĩra ũhoro wa mbĩrĩra o na kũriũka gwake.\n" +
                "ũhoro ũcio wa wendo, wendo waarĩ mũnene,\n" +
                "Ngũigua ta ngũita maithori, wendo nĩguo wangũrire.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("44. Nindakuuriruo Ni Mwathani (NZK 35)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩndakũrirwo nĩ Mwathani ũria wanjiguirĩire tha,\n" +
                "Thogora wa thakame yake nĩũnduĩte mwana wake.\n" +
                "Nĩngũũrĩtwo! Nĩngũũrĩtwo na thakame;\n" +
                "Nĩngũũrtwo! Nĩngũũrĩtwo na thakame.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩndakenirio nĩ gũkũũrwo, Gĩkeno gĩtangĩgwetwo;\n" +
                "Na nĩkuo kuonanirie wendo ũrĩa wanduire wake ki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩngerorera wega wake, Mũnene wa kũgegania;\n" +
                "Ndĩkenagia na watho ngĩinagĩra rĩĩtwa rĩake.\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩnjũĩ nĩ njigiirwo  thũmbĩ, ya muoyo kũrĩa matu-inĩ;\n" +
                "Hatigairie hanini acooke nĩguo harĩa arĩ nginye ho.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("45. Ni Muthenya Wa Munyaka (NZK 36)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩ mũthenya wa mũnyaka ũcio ndaambire gwĩtĩkia!\n" +
                "Nĩ kĩo ngenaga na ngoro, na ngĩĩraga andũ othe.\n" +
                "Mũthenya! Mwega ma! ũcio Jesũ aatheririe!\n" +
                "Nĩwe ũũmenyithĩtie, kwĩhũũga o na kũhooya.\n" +
                "Mũthenya mwega ma, ũcio Jesũ aatheririe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngwaatanĩro njega mũno, Iitũ na Jesũ mũnyeendi,\n" +
                "Nĩngũmũinĩra na Nyĩmbo, Ngĩthiaga o harĩ We.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩwe waarutire wĩra, wake ngoro yakwa thĩinĩ:\n" +
                "Nĩwe waanyonirie njĩra, na niĩ ngĩmũrũmĩrĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndĩ wa Jesũ nake wakwa, twĩ na ũraata mũnene:\n" +
                "Nĩngenaga mĩndĩ yothe, tondũ nĩ we ũndwaraga.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("46. Ngoro Yakwa No Ihote");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngoro yakwa no ĩhote, gũcokeria Ngai ngatho,\n" +
                "No rũĩmbo ngũruta nakũ rũa kũinĩra wendo wa Jesũ.\n" +
                "Nĩngegete, Nĩngegete, nĩ ũndũ wa wendo wake,\n" +
                "Nĩngegete, Nĩngegete, nĩ wendo wa Mũkũũri.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Gwĩ gĩkeno, kwagĩa thayũ, kwagĩa ũũru, kwagĩa nduma,\n" +
                "Ruo rũagĩa, o na wonje kĩhonia no wendo wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Kũrekerũo mehia makwa, ndagwa, ndĩ muoyo, na ndakua,\n" +
                "Ndirĩ na mwĩhoko hangĩ o thengia no wendo wa Jesũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("47. Riu Turaathime Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\tt1.\n" +
                "Rĩu tũraathime Jesũ, Thaa nĩnginyu tũkome,\n" +
                "Tũkuumbura wĩhia witũ, Wee Mwathani tuohere.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nduma nayo, Wee ndũũragia, O na ĩngĩneneha;\n" +
                "Andũ aaku ũikaragia, Na ndũũnginya gũcũũnga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mbaara ĩngĩkuhĩrĩria, Wee watũrigĩrĩria,\n" +
                "Na ũgwati ungĩũka, No ũgatũgitĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Tũrĩ ũrĩrĩ ũtukũ, Tũngĩkoma tũkue,\n" +
                "Hinya waku ũgaatũriũkia, Tũgaatũũranie nawe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("48. Jehova Wa Kuu Betheli");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jehova wa kũu Betheli, Nĩaateithirie andũ,\n" +
                "Ithuĩ nĩatũteithagia na maũndũ mothe.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Rĩu tũgĩthiĩ rũgeendo, Nĩtũhooe Jesũ,\n" +
                "Ateithagie twaana twitũ, Rĩu ningĩ tene na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Njĩra ĩrĩa ĩriingi, Ngai nĩarĩtuonia,\n" +
                "Nĩgeetha tũtikagie na Mathaangania.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩ wega ũtũtoongorie, Rũgeendo-inĩ rũũrũ,\n" +
                "Na thuutha ũgatũtũũria thaayũ-inĩ waku Ngai.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("49. Nguucia Hari We Jesu (NZK 33)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nguucia harĩ we, Jesũ mwega; Tũikaranagie mahiinda mothe,\n" +
                "Ngwenda ũnyiite na ũũnũmie; njikarage gĩthũri-inĩ gĩaku,\n" +
                "Njikarage gĩthũri-inĩ gĩaku.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nguucia harĩ we, ndirĩ kĩĩndũ; ndirĩ indo cia gũkũrutĩra,\n" +
                "O’ theengia ngoro heheenjeku; ũmĩthambie na thakame yaku,\n" +
                "ũmĩthambie na thakame yaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nguucia harĩ we, nduĩke waku; Nĩngeneete nĩgũtiga meehia,\n" +
                "Na ĩkeno ciothe na mwĩtĩĩo; ngwenda O Jesũ ũrĩa waambirwo,\n" +
                "Ngwenda O Jesũ ũrĩa waambirwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nguucia harĩ wee, hiingo ciothe; O kinya nginye kũu igũrũ,\n" +
                "Ngaatũũra kuo na tene; ndĩĩroragĩre ũthiũ ũcio waku,\n" +
                "Ndĩĩroragĩre ũthiũ ũcio waku.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("50. Jesu Niarihite Thiiri");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩarĩhĩte thiirĩ, Mũhonokia Mũnene!\n" +
                "Tũkũũrĩtwo nowe wiki, Mũhonokia Mũnene!\n" +
                "\n" +
                "\n" +
                "Mũhonokia Mũnene na mwega nĩ Jesũ!\n" +
                "Mũhonokia mũnene nĩ Mũrũ wa Ngai!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩ ngũgaatha wega mũno, Mũhonokia Mũnene!\n" +
                "Tondũ wa thakame yaku, Mũhonokia Mũnene!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩatheretie ngoro yakwa, Mũhonokia Mũnene!\n" +
                "Tondũ wa thakame yaku, Mũhonokia Mũnene!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩngũthiĩ thiritũ nake, Mũhonokia Mũnene!\n" +
                "Na njĩtĩkie ndeto ciake, Mũhonokia Mũnene!\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("51. Ngondu Miroongo Kenda Na Kenda");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ng’ondu mĩroongo kenda na kenda ciarĩ kiugũ-inĩ,\n" +
                "No ĩmwe yacio nĩyorĩire kũraaya na kĩhiingo,\n" +
                "Yaarĩ kũraaya na Mũrĩithi.\n" +
                "O kũraya mũno na kwarĩ nduma,\n" +
                "O kũraya mũno na Mũrĩithi.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ng’ondu ici ciothe ii haha,\n" +
                "Kaĩ itangĩkũigana,\n" +
                "Io ĩmwe ĩrekwo yũũre, Tondũ ici nĩ nyingĩ ma,\n" +
                "No Mũrĩithi akĩmũcookeria,\n" +
                "Ndathiĩ gũcaria ĩrĩa yũrĩte.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "ũtukũ ũcio mũrĩithi,\n" +
                "Aaraire gĩthaka-inĩ,\n" +
                "Na gwakĩa akĩona ng’ondu ĩo ĩgũĩte irima-inĩ.\n" +
                "Nĩkũigua aaiguire yaania ho\n" +
                "Itigairie hanini ĩkue nĩ heho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Na mũrĩithi aakinya mũciĩ,\n" +
                "Akiuga “Nĩtũkene”\n" +
                "Ng’ondu yakwa ĩrĩa ĩrorĩĩte nĩandarĩĩkia kũmĩona.\n" +
                "Na araika makĩanĩrĩra\n" +
                "Makĩuga, kenai, kenai, O ma, ng’ondu ĩrĩa ĩrorĩĩte nĩyoneka.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("52. Muhonokia, Ndoongoria");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũhonokia, ndoongoria, Ndĩ thĩ ĩna magerio;\n" +
                "Nĩndigĩrĩirio njĩra, Nĩ ndĩihũ o na irĩma;\n" +
                "Jesũ nĩ we njĩra, Mũhonokia ndoongoria!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kaana nĩ karĩraga,Nyina agagakiria;\n" +
                "O Nawe Mũhonokia Nĩũkiragia irĩra;\n" +
                "Kiria magerio makwa, Mũhonokia ndoongoria!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Rĩrĩa ngaatua gũkinya, Ndiihu nene irurume,\n" +
                "Njiga kĩhuurũko, o gĩthũri-inĩ gĩaku,\n" +
                "Reke ngaigua ũkĩnjĩĩra, Ningũgũtoongoria rĩu.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("53. Muumbi Wa Thi O Na Iria");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũũmbi wa thĩ o n oroa Nĩtwamũriruo nyũmba ĩno\n" +
                "Nĩ wĩra wa moko maitũ Twahe Ngai o na Mũrũwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Rehe wega waku nyũmba ĩno nĩguo ehia mangĩũngana,\n" +
                "Atheru o na araika, Mainagĩre wendani waku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Arĩa mena kĩeha ngoro-inĩ Mone kĩhuurũkio gĩaku\n" +
                "Mũhonokia ũmohere nao hamwe makumie Ngai.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("54. Muru Wa Ngai Niokire");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũrũ wa Ngai nĩokire, Etagwo “Wa mathĩĩna”,\n" +
                "Nĩguo atũhonokie, Mũhonokia mwega mũno.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na nĩaacirithirio, Ningĩ aahũũrirũo ma,\n" +
                "Na no We waanjoheire, Mũhonokia mwega mũno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ithuĩ twarĩ ooru ma, Nowe aarĩ mũthiingu;\n" +
                "Nĩaatũigwithanirie; Mũhonokia mwega mũno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩaambiruo mũtĩ-inĩ, “Nĩoigire,” Nĩ hũngitie,”\n" +
                "Rĩu atũire igũrũ, Mũhonokia mwega mũno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Rĩrĩa agooka, Mwathani, atũtware igũrũ,\n" +
                "Hĩndĩ ĩo nĩtũkaina, “Mũhonokio Arogoocwo”\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("55. Ninguinira Ngai Hoti (NZK 38)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kuo. Nĩngũinĩra Ngai hoti ya maũndũ mothe,\n" +
                "Ombire irĩma na iria o na matu mothe,\n" +
                "ũũgĩ wake watũmire riũa rĩthamake,\n" +
                "Mweri wathage ũtukũ na njata ikamũigua\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Wega wa Mwathani witũ ũtũheaga irio,\n" +
                "Arĩkia kũũmba indo ciothe oigire-nĩ njega,\n" +
                "Kũria guothe ndaikia maitho no ũrirũ waku,\n" +
                "Tĩĩri ũyũ o na matu nĩ cia kũgegania.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mĩtĩ yothe na mahũa ii na riiri waku,\n" +
                "Matu mathiũrũrũkaga o rĩrĩa ũngĩenda,\n" +
                "Ciũmbe ciothe ii na muoyo uumĩte harĩ we,\n" +
                "Kũrĩa guothe tũngĩũrĩra no ũgakorũo\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("56. Mwathani Ninjiguaga (NZK 39)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani nĩnjiguaga angĩ mekũraathimwo,\n" +
                "Ndakũhooya undaathime na kĩraathimo gĩaku.\n" +
                "\n" +
                "\n" +
                "O na niĩ, O na niĩ, ũndaathime o na niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ithe witũ ndũkandige tondũ ndirĩ na hinya.\n" +
                "Na ũkanjĩkĩra na nĩnjũũĩ nĩũnyeendete o na niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ nĩwe Mũhonokia wa arĩa mamwĩhokete,\n" +
                "Nĩũmendeete na nĩnjũũĩ nĩũnyeendete o na niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndeithagia Roho Mũtheru ndigane na waganu,\n" +
                "Nĩngwenda ũtheri waku ũũke gwakwa Jesũ rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Theria Jesũ ngoro yakwa na thakame ĩo yaku,\n" +
                "ũnjiyũrie rĩu, Mwathani na ũhooreri waku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t6.\t\n" +
                "Mwathani, ndũkanandige tondũ ndagũkaĩra,\n" +
                "Nĩngwenda ũndaathimage na kĩraathimo gĩaku.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("57. Wee Roho Mutheru Uka (NZK 40)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1. \n" +
                "We Roho Mũtheru ũka ũthambie ngoro yakwa\n" +
                "ũtherie meciiria makwa ũka ũnjiyũre rĩu\n" +
                "\n" +
                "\n" +
                "We Roho Mũtheru ũka ũnjiyũre rĩu,\n" +
                "ũtherie meciria makwa ũka ũnjiyũre rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ona ingĩaga gũkũona nĩũnjiyũraga ngoro,\n" +
                "Naniĩ nĩ mbatairio nĩwe ũka ũnjiyũre rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " Ndirĩ na hinya mũiganu nĩngũkũinamĩrĩra,\n" +
                "Roho Mũtheru wa Ngai ũnjiyũrie hinya rĩu\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "ũthambie na ũhorerie ũhonie ũndathime,\n" +
                "ũnjiyũre ngoro yakwa tondũ nĩwe Mũhoti.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("58. We Roho Mutheru Ma (NZK 41)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "We Roho Mũtheru ma, We mũtongoria wa ma,\n" +
                "ũtũnyite moko riu rũgendo-ini rũrũ.\n" +
                "Tũigue mũgambo waku, Mũgambo mũhoreri,\n" +
                "“Mũgendi ũka hari nii,  ningũgũkinyia mũcii.”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "We nowe murata ki, uteithio wa  hakuhi\n" +
                "Nina nganja na guoya, Hindi iria twi nduma-inĩ,\n" +
                "Tũigue mũgambo waku, Mũgambo mũhoreri,\n" +
                "“Mũgendi ũka hari nii,  ningũgũkinyia mũcii.”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Wira witu wathira, Tũninirwo minoga,\n" +
                "Twiriragirie igũrũ, Na ngatho na mahoya,\n" +
                "Tũigue mũgambo waku, Mũgambo mũhoreri,\n" +
                "“Mũgendi ũka hari nii,  ningũgũkinyia mũcii.”\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("59. Wee Roho Wa Matu-ini (NZK 42)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Wee Roho wa matu-inĩ, Thikĩrĩria mahooya!\n" +
                "Thondeka ciikaro ciaku, Ngoro-inĩ ciitũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Twehereire nduma iitũ, Na ũtheri ũcio waku,\n" +
                "Hitho yaku tũmĩmenye, O na thaayũ waku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndũgĩtũthaambie na mwaki, Cina meehia maitũ,\n" +
                "Nacio ngoro ciitũ ituĩke, Hekarũ ya Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ningĩ ndũgĩũke ta ime, Nĩguo ũtũhuurũkie,\n" +
                "Nacio ngoro irĩa nyũmũ, Nĩ ikaraathimwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Roho Mũtheru ndũhaane, Hĩndĩ ya Bendegosto,\n" +
                "Ndũkĩhuunjie ũhonokio, Ndũũrĩrĩ-inĩ ciothe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("60. Riua Nirithuire");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Riũa nĩ rĩthũire Na nĩgũtuku;\n" +
                "Gũkagĩa na nduma; Njata nĩ nyumu;\n" +
                "Mbũri o na ng’ombe, Rĩu nĩ hingĩre;\n" +
                "Andũ mainũke, Rĩu no makome.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Arĩa anogu, Jesũ ũmahe toro;\n" +
                "Na niĩ tũma ngome, Na kĩraathimo;\n" +
                "Twaana tũkĩroota, Tũroote nawe,\n" +
                "Na andũ marĩ njĩra, ũmatoongorie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Na arĩa aruaru, Manyamarũre;\n" +
                "Mũndũ ũkwagana, ũmũgirie wee;\n" +
                "Tũma Araika manjikaragie,\n" +
                "Mandigicagĩrie ũtukũ wothe,\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("61. Ndaagia Gikeno Na Ngwataniro (NZK 43)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndagia gĩkeno na ngwatanĩro ingĩĩhokaga Jesũ wiki;\n" +
                "He kĩrathimo ningĩ na thayũ, ingĩĩhokaga Jesũ wiki.\n" +
                "\n" +
                "\t\n" +
                "Ndamwĩhoka, ndicoka kuona ũgwatĩ;\n" +
                "Nĩngwĩhoka, nĩngwĩhoka Mwathani Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t2.\n" +
                " Nĩndĩhotaga rũgendo rũrũ, ingĩĩhokaga Jesũ wiki;\n" +
                "Nayo njĩra nĩ ĩrĩhũthagĩra, ingĩĩhokaga Jesũ wiki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " Ndirĩ na ũndũ wa gwĩtigĩra, ingĩĩhokaga Jesũ wiki;\n" +
                "Nĩarikoragwo hakũhĩ na nĩĩ, ingĩĩhokaga Jesũ wiki.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("62. Gutiri Na Muraata Ta Jesu (NZK 44)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Gũtirĩ na mũrata ta Jesũ, Gũtirĩ o na ũmwe,\n" +
                "ũngĩhonia mĩrimũ ya ngoro, Gũtirĩ o na ũmwe\n" +
                "\n" +
                "\t\n" +
                "Jesũ nĩoĩ mathĩna maitũ, Nĩarĩtũtongoragia,\n" +
                "Gũtirĩ na mũrata ta Jesũ, Gũtirĩ o na ũmwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Gũtirĩ ũngĩ mũthingu take, Gũtirĩ o na ũmwe,\n" +
                "Nũũ wanona mũhoreri take, Gũtirĩ o na ũmwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Wanona mũrata ũngĩ take, Gũtirĩ o na ũmwe,\n" +
                "ũtũkũ ndangĩgũtiga wiki, Gũtirĩ o na ũmwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Kwĩ mwana wanaregwo nĩ Jesũ, Gũtirĩ o na ũmwe,\n" +
                "Kana mwĩhia wanaregwo nĩwe, Gũtirĩ o na ũmwe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("63. Utheri Wi Kirima-ini (NZK 45)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1. \n" +
                "ũtheri wĩ karĩma-inĩ, ũtheri wĩ iria-inĩ,\n" +
                "Mĩrũri ĩmũrĩkĩte kũrĩa kũruru wega,\n" +
                "No ũtheri ũngĩ mwega ũiyũire ngoro yakwa,\n" +
                "Tondũ Mwathani e thĩinĩ, Ta mĩrũri ya riũa.\n" +
                "\n" +
                "\n" +
                "Nĩ ũkengi mwega wa riũa, ũmũrĩkĩte ngoro,\n" +
                "Jesũ athekia hanini, Kĩeha no gĩgathira.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ngũgũithia kĩeha tĩĩri-inĩ, O ta nguo ndehũku,\n" +
                "ũhumbe gĩkeno gĩaku hotage gũgũkumia,\n" +
                "Mũciĩ ũcio wa matu-inĩ, kũu wendaga njũke,\n" +
                "Meciria na ngoro yakwa, nĩigũkũinamĩrĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " Mwathani nĩũngũrĩte O hamwe na indo ciakwa,\n" +
                "Tawa ũcio ũnjakĩirie, nĩ wa gũkũgocithia\n" +
                "Mũciĩ ũcio wa matu-inĩ, kũu wendaga njũke\n" +
                "Meciria na ngoro yakwa, nĩigũkũinamĩrĩra.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("64. Guikaraga Hari Jesu (NZK 46)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Gũikaraga harĩ Jesũ, Nĩ mũnyaka mũnene;\n" +
                "Tondũ E na ndeto njega, Irĩ na kĩraathimo.\n" +
                "Thiaga ho hĩndĩ ciothe, Magũrũ-inĩ ma Jesũ\n" +
                "Ndirikaine wendo wake, ũrĩa ũngucĩĩtie ngoro.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Gũikaraga harĩ Jesũ, O magũrũ-inĩ make,\n" +
                "Hau nĩ handũ heega ma; Ha kũiga meehia makwa.\n" +
                "Magũrũ-inĩ make Jesũ, Thiaga ho kũhooya,\n" +
                "We nĩ aheaga ũhoti, Na kũhuurũkia ngoro.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "ũndaathime Mũhonokia, Ndĩ magũrũ-inĩ maku,\n" +
                "ũnjũũthĩrĩrie na wendo, Nĩguo nyone ũthiũ waku.\n" +
                "Mwathani he wendo waku, Nĩguo andũ othe moone,\n" +
                "Niĩ ndĩ na Mũhonokia, O we ũthiingu wakwa.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("65. Mwaathani Ningwendete Ma (NZK 48)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani  nĩngwendete ma, kũrĩ ikeno ciothe,\n" +
                "Tondũ nĩũhete thayũ, Ngoro-inĩ yakwa.\n" +
                "\n" +
                "\t\n" +
                "Ngumo ndĩrĩ yahunjio biũ, Ya wendani waku.\n" +
                "Ngumo ndĩri yahunjio biũ, Ya thakame yaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ngũigua wĩ hakuhĩ naniĩ, gũkĩra andũ a thĩ ĩno,\n" +
                "Rĩciiria rĩega no rĩaku, Rĩkĩrĩte rũĩmbo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " ũnjĩkĩrĩte gĩkeno ndingĩaga gũkena\n" +
                "Guca nĩ ũndũ ũnyendete, ndingĩigua gĩkeno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                " Gũkahana atĩa Mwathani, Twatũũrania nawe,\n" +
                "Na tũtwaranage nawe, We Mwathani wakwa.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("66. Ndina Muraata Munene (NZK 49)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndĩ na Mũraata Mũnene, Itaarĩ nake mbere;\n" +
                "Nĩaanyendaga mũno ma, No niĩ ndiamenyaga;\n" +
                "Nĩanguucirie ndĩ mwende, Ngĩremwo nĩ kũrega,\n" +
                "Na rĩu ndĩ wake mwene, o nake rĩu no wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndĩ na Mũraata mũnene: Kaĩ nĩ niĩ ndanendwo;\n" +
                "Nĩoragirwo ũũru ma, nĩgeetha nyone Muoyo;\n" +
                "Ngũmũhe kĩ, Mũngũũri We? Ndĩ kĩĩndũ rĩu gĩakwa?\n" +
                "Aca, ciothe, na niĩ mwene, nĩ cia Mũrata wakwa!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndĩ na Mũrata mũnene; Arĩ na hinya wothe;\n" +
                "Nĩakangitĩra wee mwene, kinya ngakinya gwake;\n" +
                "Nĩnguona Mũciĩ na Ngoro, Ngawona na ngakena;\n" +
                "Ngũruta wĩra wake rĩu, Na thuutha ngahuurũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndĩ na Mũrata Mũnene; Gũtirĩ mwega take,\n" +
                "Nĩ Mũrutani Mũnene, Na mũgitĩri mwega;\n" +
                "Gũtirĩ ũndũ ũngĩhota, kũndigithania nake,\n" +
                "Gwake njĩkĩrĩte nanga, ya tene o na tene.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("67. Mwathani mumenyagirire (NZK 50)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani amũmenyagĩrĩre O nginya tuonane rĩngĩ,\n" +
                "Mũikare kiugũ-inĩ gĩake O nginya tũkonana rĩngĩ.\n" +
                "O nginya tũkonana rĩngĩ, Magũrũ-inĩ ma Jesũ\n" +
                "O nginya tũkonana rĩngĩ, Mwathani nĩamũmenyerere.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mwathani amũmenyagĩrĩre, ruungu rua mathagu make;\n" +
                "Mũrĩage irio cia Mana, O nginya tũkonana rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mwathani amũmenyagĩrĩre ũũru wathimba ta matu\n" +
                "Amũkumbatĩre wega, O nginya tũkonana rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mwathani amũmenyagĩrĩre Bendera ya wendo wake,\n" +
                "ĩikare igũrũ rĩanyu, O nginya tũkonana rĩngĩ\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("68. Jesu Mukuuri wakwa kuna (NZK 51)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ Mũkũũri wakwa kũna, Gĩkeno thaayũ nĩaheaga;\n" +
                "Theragio nĩ thakame yake, Ningĩ ũhonokio nĩngayaga.\n" +
                "\n" +
                "\n" +
                "ũhoro mwega rũĩmbo rũakwa, Ngoocage Jesũ hĩndĩ ciothe,\n" +
                "ũhoro mwega rũĩmbo rũakwa, Ngoocage Jesũ hĩndĩ ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngenagio nĩ kwĩheanaga, Ngoonaga Jesũ na wĩtĩkio,\n" +
                "Andeehagĩra araika, Maamenyerere ũhonokio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndaathimagũo nĩũndũ wa Jesũ, Anjoire o ũrĩa ndatariĩ;\n" +
                "Ndamweterera ngirĩrĩirie, Nĩngũiganwo nĩ wega wake.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("69. Nihari Mwihoko Wa Goro (NZK 162)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩharĩ mwĩhoko, wa goro mũno ma,\n" +
                "Gũkĩra ikeno cia thĩĩno, Iria itarĩ kĩene.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kwĩ na njata theru, Irutaga ũtheri,\n" +
                "Kũrĩ ithuĩ hĩndĩ ya gĩkuũ, Na nĩyo kũriũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ngoro ciarũario, gwĩtigĩra, ngaanja;\n" +
                "Mũgaambo nĩ ũtwĩraga, Ngai nĩatwendeete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mũgaambo nĩũroiga uumĩte Kalvari,\n" +
                "Njata nĩ ũtheri wa igũrũ, Na mwĩhoko witũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("70. Kwi Na Kiraathimo (NZK 47)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kwĩ na kĩraathimo, Tuohanio nĩ wendo,\n" +
                "Wendo wake Jesũ nĩguo ũtuohanagia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Rekei tũhooe, Mbeere ya Ithe witũ,\n" +
                "Guoya witũ, na mathĩĩna Jesũ no ũndũ ũmwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩtũgwatanĩire, Mĩnyamaaro yothe,\n" +
                "Kaingĩ kaingĩ nĩtũitaga Maithori ma kĩeha.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Tũgĩtigana Nĩtũiguaga kĩeha,\n" +
                "No nĩtũkoonana rĩĩngĩ Igũrũ matu-inĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("71. He Bibilia Njata Ya Gikeno (NZK 52)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "He Bibilia, njata ya gĩkeno, Nĩguo agendi mone ũtheri,\n" +
                "Thayũ rĩu ndũngĩkĩgirĩrĩka, tondũ Jesũ nĩatũhonokirie.\n" +
                "\t\n" +
                "\n" +
                "He Bibilia nĩcio ciugo theru, \n" +
                "ũtheri ũcio nĩũkũndongoria,\n" +
                "Watho kĩrĩkanĩro ona wendo, \n" +
                "Irũmanĩrĩre nginya tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "He Bibilia rĩrĩa ndĩ na kĩeha, ndataangwo nĩ meehia o na guoya,\n" +
                "ũhe ciugo icio ciaririo nĩ Jesũ, Ndĩmuonage e hakuhĩ na niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "He Biblia nĩguo hote kuona, mogwati maria me gũũkũ thĩ ĩno,\n" +
                "ũtheri ũcio wa Mwathani Jesũ, ũtuonagia wega njĩra ya ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "He Bibilia ũtheri wa muoyo, Na nĩguo wacoka ũkandiũkia,\n" +
                "Na ũnyonie ũtheri wa matuinĩ, Na nyone ũkengi wa Mwathani.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("72. Ninyendeete Ibuku Ria Ngai (NZK 62)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩnyendeete Ibuku rĩa Ngai, Ibuku Itheru rĩega kũna,\n" +
                "Rĩnyonagĩrĩria kwa Ngai, kũrĩa gũtarĩ na thĩĩna.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\tt2.\t\n" +
                "Thĩĩnĩ warĩo nĩrĩnyonagia, mbica ya Mwathani wakwa;\n" +
                "Ningĩ nĩrĩo rĩndutaga, gũkenera Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Na rĩrĩa ndĩ gũũkũ thĩ ĩno, rĩnyonagia wendo wa Ngai;\n" +
                "Ndĩrĩthomaga na wĩtĩkio, nĩguo ngĩe na thaayũ wa Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Hau nĩho hoonekaga, Wendo, wa ũrĩa Wanyũũmbire;\n" +
                "Na ũrĩa mũũgĩ nĩarĩonaga, ithima cia ũũgĩ wothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "ũtheri wa thĩ toongoria, makinya makwa nduma-inĩ!\n" +
                "Wee kĩmũrĩ mũrĩkĩra, njathĩke ndĩ rũgeendo-inĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("73. Ninyendeete Kuhuunjia (NZK 53)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩnyendeete kũhuunjia, ũhoro wa Jesũ,\n" +
                "Wa Jesũ We Mũnene, na wendo mũingĩ ma;\n" +
                "Nĩnyendeete kũhuunjia, na indo ciakwa ciothe;\n" +
                "Niĩ mwene nĩnjamĩte ngamenya nĩ wa ma\n" +
                "\t\n" +
                "\n" +
                "Nĩnyendeete kũhuunjia, ũhoro wake Jesũ,\n" +
                "Wa Mwathani wa ũnene, na wendani wake.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩnyendeete kũhuunjia, magegania make,\n" +
                "Natũngĩmeciirĩria, makĩrĩte thahabu.\n" +
                "Nĩnyendeete kũhuunjia, uhoro ucio mwega.\n" +
                "Na niĩ nĩnyendaga ma, gũkwĩra ũhoro ũcio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Kwenda kĩhuunjia ũhoro, nĩkũngenagia ma,\n" +
                "Cama waguo nĩ mwega, o na ndũngĩthira,\n" +
                "Nyendeete kũhuunjĩria, arĩa me nduma-inĩ;\n" +
                "Matirĩ na mũhuunjia, wa ũhoro ũrĩa mwega.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("74. We Niukeyuumia Ruuho");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "We nĩũkeyũmia rũhuho ruoka, Gwoka rũhuho rũrĩa rũa ũũru,\n" +
                "Rwĩ na makũmbĩ o ta ma iria, nanga yaku nĩĩkarũmia wega.\n" +
                "\n" +
                "Twĩ na nanga ngoro thĩinĩ, ya kũrũmia makũmbĩ moka;\n" +
                "Ya kũhocia hau rũaro-inĩ, rũaro nĩ Jesũ na wendo wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nanga yaku yakorwo nĩ njega, nĩkarũmia yanyitwo nĩ Jesũ,\n" +
                "Ngoro yake yohanio na yaku, nĩũkahootana angĩgũteithia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nanga yaku nĩĩkarũmia wega, kũrĩa guothe ũngĩiguĩra guoya,\n" +
                "O na werũo ũgwati nĩ mũũku, gũtirĩ ikũmbĩ rĩngĩgũthika.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nanga yaku nĩĩkarũmia wega, ũngĩkaigua watonya gĩkuũ-inĩ,\n" +
                "Nditi cia maĩ ma ũũru wothe, Itigatũtoria atũteithia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Rĩrĩa tũkona mũciĩ mũthaka, Wĩ na mĩrango ya ruru nduru,\n" +
                "Tũhocie nanga nginya tũkinye, nginya kĩhuhũkanio gĩthire.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("75. Ningwenda Kumenya Jesu (NZK 54)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩngwenda kũmenya Jesũ, ngirĩrĩrie kũmũmenya,\n" +
                "Menye wendani wake, na ũhonokio ũrĩa mũiganu.\n" +
                "Makĩria makĩria, menyage Jesũ,\n" +
                "Menye wendani wake na ũhonokio ũrĩa mũiganu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na nĩngwenda nyone Jesũ na ngirĩrĩrie kũmũigua,\n" +
                "Akĩara Ibuku-inĩ rĩake, eyonithanie kũrĩ niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩngwenda kũmenya wega; ngirĩrĩrie gũkũũrana,\n" +
                "ũrĩa we mwene endaga njĩkage ũrĩa ũmũkenagia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩngwenda njikare nake, matereta-inĩ makĩria,\n" +
                "Na ngirĩrĩrie kuonia andũ arĩa angĩ ũhonokio wake.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("76. Tuhaandaga Gwakia (NZK 55)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũhandaga gwakĩa na mĩthenya yothe,\n" +
                "Nginya o hwaĩ-inĩ mbeũ cia wendo,\n" +
                "Na nĩ twetereire hingo ya kũgetha.\n" +
                "Tũgakenera ma magetha maitũ.\n" +
                "\n" +
                "Magetha maitũ, magetha maitũ,\n" +
                "Tũgakenera ma magetha maitũ.\n" +
                "Magetha maitũ, magetha maitũ,\n" +
                "Tũgakenera ma magetha maitũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tũhande riũa-inĩ, ona ciĩruru-inĩ,\n" +
                "Heho na rũhuho tũcitorie biũ,\n" +
                "Thutha-inĩ nĩtũrĩnina wĩra witũ,\n" +
                "Tũgakenera ma magetha maitũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũhandĩre Mwathani mĩthenya yothe,\n" +
                "Hĩndĩ ya mathĩĩna ona maithori,\n" +
                "Maithori maathira, niagatwamũkĩra,\n" +
                "Tũgakenera ma magetha maitũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("77. Mutihonokiei Aria Mekuura (NZK 56)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Mũtihonokei arĩa mekũũra \n" +
                "Arĩa megũkua tondũ wa meehia\n" +
                "Na arĩa mekũgwa mũtikĩmooe\n" +
                "Mũmeere wega ũhoro wa Ngai\n" +
                "\n" +
                "\n" +
                "Mũtihonokiei arĩa mekũũra \n" +
                "Mũhonokia no ametereire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\n" +
                " O na maamũrega no ametereire\n" +
                "Endaga mũno mooke meerire\n" +
                "Nĩtũmathaithe ma na ũhooreri\n" +
                "Maamwĩtĩkia nĩekũmoohera.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Ngoro-inĩ cia andũ,ũcio shaitani\n" +
                "Nĩarehaga thĩĩna na kĩeha\n" +
                "No wega wa Jesũ nĩmuohanĩri\n" +
                "Akaamahonia na amahonokie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Tũthiĩ tũkahonokie arĩa morĩte\n" +
                "Wĩra wa Ngai wĩna kĩheeo\n" +
                "Tũmaguucie wega na ũhooreri\n" +
                "Matwarĩrwo Jesũ amahonokie.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("78. Ndiraririkana Bururi Muthaka (NZK 58)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndĩraririkana bũrũri mũthaka Ngatonya riũa rĩgĩthũa,\n" +
                "Wega wa Jesũ wanginyia kuo\n" +
                "Thũmbĩ yakwa nĩngagemerio na njata?\n" +
                "\n" +
                "\n" +
                "Nĩngona, tũcata, Tũcata thũmbi-inĩ yakwa\n" +
                "Narĩo riũa rĩathũa?\n" +
                "Ndeyona gĩkundi-inĩ kĩa arĩa endeku\n" +
                "Nĩngona ng’emeirio na njata?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ngũhoya na ndute wĩra wa Mwathani Ndĩmũrehere andũ aingĩ,\n" +
                "Nĩguo ngekĩrĩrũo tũcata thũmbĩ-inĩ ĩhenagie O ta gĩciicio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Ndamuona nĩngakena mũno Ndamũtwarĩra arĩa nyonetie njĩra,\n" +
                "Nĩguo njĩkĩrũo tũcata thũmbĩ-inĩ Ihenagie o ta gĩciicio.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("79. Ndukaarege Wira (NZK 57)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndũkaarege wĩra wa Mwathani Jesũ, Ikara wĩhaarĩirie, kũruta wĩra ũcio:\n" +
                "Ngai nĩaragwĩta ũthiĩ o kũrĩa ekwenda, Nawe nĩũgakena nĩ\n" +
                "ũndũ wa wĩra ũcio.\n" +
                "ũka, Rĩu ndũkarege wĩra:  ũũke Rĩu! Wĩra-inĩ wa Jesũ,\n" +
                "Ndũkarege Wĩra wa Mwathani Jesũ, Nĩgeetha thuutha-inĩ ũgathiĩ igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndũkaarege Wĩra wa Mwathani Jesũ; ũkũrega nĩkĩ? Ruta wĩra rĩu.\n" +
                "Magetha nĩ maingĩ, Agethi nĩ anyinyi, Ndũkĩĩrutanĩrie wĩra-inĩ wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndũkaarege wĩra wa Mwathani Jesũ, ũrĩ ũgwati-inĩ warega wĩra ũcio.\n" +
                "Hingo ĩno nĩ ya tha, Nĩguo Jesũ ekuuga, Ndũkiumbũre meehia.\n" +
                "Nĩgeetha woherwo.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("80. Ninyonete Murata Wa Goro (NZK 61)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩnyonete mũraata, Na nĩ wa goro ma,\n" +
                "Na nĩanyendeete ma, Wendo mũingĩ, ĩtĩkia ũguo:\n" +
                "Gũtigithũkanio nake, Aca, ũguo ndingĩenda,\n" +
                "Nĩtũikaraga nake: Jesũ na niĩ.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na rĩmwe nĩnyonaga Niĩ itarĩ hinya,\n" +
                "Na niĩ ngakĩmwĩhokaga, Ngĩenda ũteithio wake:\n" +
                "Nĩandoongoragia njĩra-inĩ handũ he na ũtheri,\n" +
                "Nĩtũceeraga nake, Jesũ na niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Na rĩmwe nĩnogaga, na gĩkeno gĩakwa,\n" +
                "Ngamwĩra mathĩĩna makwa, O na ikeno ciakwa,\n" +
                "Nake anjĩraga njĩkage, O maũndũ mega,\n" +
                "Na nĩtwaragia nake. Jesũ na niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Na nĩoĩ nĩnyeendaga, kũguucĩrĩria andũ,\n" +
                "Kwoguo we nĩandwaraga, Tũkahuunjie ũhoro,\n" +
                "Ngahuunjie wendani wake, Nĩaatũkuĩrĩire;\n" +
                "Na tũhuunjagia nake: Jesũ na niĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("81. Andu Rutaai Wira (NZK 59)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Andũ rutaai wĩra,rĩu gũtanatuka\n" +
                "Mũikare mwĩhũũgĩte mũkirĩrĩirie\n" +
                "Tungatĩrai Jesũ na horo ũcio mwega\n" +
                "Huunjiai kũndũ guothe andũ othe maigue.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Andũ rutai wĩra rĩu nĩ kũraatuka\n" +
                "Andũ aingĩ kũu kwanyu me nduma-inĩ\n" +
                "Mũtikareke moore ihinda no rĩrĩ\n" +
                "Jesũ no e gũcooka twarĩĩkia wĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Andũ rutai wĩra gũkirie gũtuka\n" +
                "Andũ othe kũu kwanyu Ngai nĩ ameenda\n" +
                "Ithue tũmũmenyeete nĩtũmahuunjĩrie \n" +
                "Nĩguo Jesũ acooke tũkaamũkenia. \n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("82. Ukaani Tukene Andu A Mwathani");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "ũkaani tũkene, andũ a Mwathani,\n" +
                "Nĩtũthiĩ kũu Bethilehemu,\n" +
                "Nĩ kuo acĩarĩirwo, Mũhonoka witũ.\n" +
                "\n" +
                "\t\n" +
                "ũkaani tũmũhooe, ũkaani tũmũhooe,\n" +
                "ũkaani tũmũhooe, Mwathi Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nake nĩwe Ngai, na nowe ũtheri,\n" +
                "Na ndaaregire gũtuĩka ta kaana,\n" +
                "Nĩko Mwathani, gaaka gaciarĩtwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mũtimũkumiei, inyuĩ Araika,\n" +
                "Inyuothe mũtikĩgaathe Jesũ,\n" +
                "Muuge arogoocwo Jesũ,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mwathani witũ, twagũkũngũĩra,\n" +
                "Jesũ twakũhe ũgooci wothe,\n" +
                "Nĩwe ũhoro, ũtuĩkĩte mũndũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("83. Wee Niwe Wandutiire (NZK 121)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Wee nĩwe wandutĩire, Igongona rĩiganu\n" +
                "Nĩwanyamarĩkire Mutĩ-inĩ wa kĩrumi\n" +
                "Ugĩtuonia kwĩnyihia Ugĩthambia nyarĩrĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngoro yakwa wahota kumena uguo ekire?\n" +
                "Nowe ndaigana kwĩhia koruo e haha rĩu\n" +
                "Ndamuthambia nyarĩrĩ Nĩguo ndĩnyihie take.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩ gĩkeno kĩnene Ngĩkuonia wendo wakwa\n" +
                "Nĩ kahinda uheete Na nĩunjuthĩrĩirie\n" +
                "Wone kwĩnyihia gwakwa, Ndurie ndu mbere yaku.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("84. Riria Nguona Mutharaba (NZK 63)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Rĩrĩa nguona mũtharaba,\n" +
                "Hau Kristo anguĩrĩire;\n" +
                "Nyonaga indo itarĩ bata,\n" +
                "Mwĩtĩo nĩndĩũthũire.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndiagĩrĩirũo nĩ kwĩgooca,\n" +
                "No ndĩgoocire na Jesũ,\n" +
                "Ndikwenda waganu rĩĩngĩ,\n" +
                "Jesũ nĩanjiganĩte.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Merirĩria o na wendo,\n" +
                "Injiũre mwĩrĩ wothe;\n" +
                "Jesũ no We ndĩrirĩirie,\n" +
                "Wendo waku nĩ mũiganu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Indo ciothe cia gũũkũ thĩ,\n" +
                "ũngĩheeo ti njiganu;\n" +
                "Ndĩ na thiirĩ waku Jesũ,\n" +
                "Wa indo, O na muoyo wakwa\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("85. Ni Utuku Gukiriitwo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩ ũtukũ gũkirĩĩtwo,\n" +
                "Thĩ yothe ĩĩ toro;\n" +
                "No Mariamu na Jusufu,\n" +
                "Meroreire Mwana mũthaka.\n" +
                "”Koma, Mwana mwega”\t\n" +
                "Koma, Mwana mwega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.  \n" +
                "Nĩ ũtukũ; Gũkirĩĩtwo;\n" +
                "Arĩithi mokĩrio,\n" +
                "Nĩ ũtheri wa rũĩmbo rũega,\n" +
                "Ciumĩte igũrũ- matuinĩ,\n" +
                "“Kristo nĩ mũũku thĩ,\n" +
                "Mũkũũri nĩ mũciare”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩ ũtukũ gũkirĩĩtwo,\n" +
                "ũkeengi ũgĩũka thĩ\n" +
                "Wa mwana ũcio wa igũrũ;\n" +
                "Nĩatũreheire ngoga ciake.\n" +
                "“Kristo nĩ mũũku thĩ,\n" +
                "Mũkũũri nĩ mũciare”\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩ ũtukũ gũkirĩĩtwo,\n" +
                "Araika makĩina;\n" +
                "“Ngumo ya ũhoreri wake,\n" +
                "ũroiga kũrĩ andũ othe,\n" +
                "Kristo nĩ mũũku thĩ,\n" +
                "Mũkũũri nĩ mũciare.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("86. Mee Ha Aria Mamenyagirira");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Mee ha arĩa mamenyagĩrĩra Maathani ma Ngai wa Igũrũ,\n" +
                "O hamwe na wĩtĩkio wa Jesũ, Acio nĩo andũ aria atheru\n" +
                "\n" +
                "\n" +
                "Watho wa Ngai, watho wa Ngai,\n" +
                "Watho wa Ngai, watho wa Ngai, Na wĩtĩkio wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Arĩa othe mamenyagĩrĩra, Maathani ma Ngai wa Igũrũ,\n" +
                "O hamwe na wĩtĩkio wa Jesũ, Gĩkeno nĩ kia andũ aria atheru.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Gũkena nĩ arĩa marĩkuaga, thutha ũyũ me thĩinĩ wa Jesũ,\n" +
                "Mahurukage wĩra mũritũ, wĩra ũcio wao ndũkora.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                " Menyerera thũmbĩ yaku wega, Mũndũ ndakae gũthiĩ nayo,\n" +
                "Mwathi Jesũ e hakuhĩ gũũka, Tũrĩhwo mũndũ wĩra wake.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("87. Jesu Niaretana Mugunda");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩaretana mũgũũnda,\n" +
                "Akĩaranĩria agethi,\n" +
                "Kũgetha nĩkuo bũrũri ũyũ,\n" +
                "Magetha nĩmo hĩndĩ ya ciira.\n" +
                "\n" +
                "\n" +
                "Hĩndĩ ya ciira ũcio wake,\n" +
                "O nao Araika nĩmakagega,\n" +
                "Jesũ arĩĩkia kũgarũra,\n" +
                "Thĩ ĩno na igũrũ guothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mũgũũnda wa Jesũ nĩ mũnene,\n" +
                "Nĩtũhurunjũkĩre kuo ithuothe,\n" +
                "Nĩtũmeyithiei andũ othe,\n" +
                "A ituri ciothe ciothe cia thĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Magetha mĩgũũnda-inĩ nĩ moomũ,\n" +
                "Thĩ yothe ũrĩa yaramĩte,\n" +
                "Agethi nĩ anini a kũgetha,\n" +
                "Magetha nĩ maingĩ nĩmekũũra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "ũkĩrai ciana cia kĩrĩĩndĩ,\n" +
                "Toonyai wĩra-inĩ wa Mwathani.\n" +
                "ũkĩrai tũhiĩ mũgũũnda wake,\n" +
                "Iguai mbugĩrĩrio na gĩkeno.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("88. Jesu Mwega Mutheru");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ, mwega, Mũtheru, Njigua rĩu mwaana waku;\n" +
                "Nĩngwenda gũkũinĩra, O kĩrooko gwathera.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩwe watũma riũa, Rĩũke gũtũkenia,\n" +
                "Rĩrutĩre ũtheri, Ciũmbe ciaku gũũkũ thĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nyoni rĩu nĩikũgaamba, Ta arĩ nyĩmbo ikũina;\n" +
                "Nĩwe igũkumia, Na mĩgaambo ĩo yacio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Wee, Mũrĩithi wa nyoni, ũ’ he irio o na niĩ,\n" +
                "Na ũ’ he Roho mũtheru: Nĩwe irio cia ngoro.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Ndeithia ũmũthĩ, Jesũ, Nduĩke mwega mwathĩku:\n" +
                "Maũndũ mothe makwa, Nyonia njĩke wega ma.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("89.  Ugihanda Na Maithori");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "ũngĩhanda na maithori ũhande na wendo mũingĩ,\n" +
                "Ndũkanoge, ndũgakome, Nĩũgũikũrũkĩrio tha.\n" +
                "\n" +
                "\n" +
                "Ta rora wone mĩgũnda, cia Ngai nĩiroma\n" +
                "No agethi meha rĩu magetha nĩmakinyu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ireera nĩrĩikũrũku Mĩrũri nĩĩmũrĩkĩte,\n" +
                "Maciaro nĩũkuona meho Hingũria maitho wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Handa na tiga kũnoga Nina guoya wa ngoro,\n" +
                "Ruta wĩra o ta Nyama Na ndũngĩaga magetha.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("90. Turaigua Witano ");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Turaigua witano turi haraihu, Huunjiai (muno) Uhoro;\n" +
                "Ngoro cia eehia nitucihonokie, Huunjiai (muno) Uhoro;\n" +
                "\n" +
                "\n" +
                "Nituhuunjie uhoro wa ma, Ucangarae muno,\n" +
                "Nituhuunjie uhoro wa ma, Ucangarare muno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nitwititwo tukahuunjie Uhoro, Huunjiai (muno ) Uhoro,\n" +
                "Jesu niamiruo mutharaba-ini, Hiinjiai (muno) Uhoro,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nituhooe wega ucio wa Ngai, Urehwo (muno) kuri ithui,\n" +
                "Na Roho ucio wa Kristo tumuone,Huunjiai (Uhoro) huunjiai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Tutikanogio ni wira wa Ngai, Huunjiai (Uhoro) Mwega;\n" +
                "Nitukaaheo thuumbi iria ya muoyo, Huunjiai (Uhoro) Mwega.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("91. Gwatiai Mataawa Manyu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Gwatiai matawa manyu tondũ nduma nĩgũũka,\n" +
                "Nduma nene kũndũ guothe Mwathani atanacoka.\n" +
                "\n" +
                "\t\n" +
                "Gwatiai matawa manyu\n" +
                "Mũrĩ na guoya mũnene,\n" +
                "Mwathani e hakuhĩ gũũka,\n" +
                "Matawa nĩ magwatio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Andũ ngiri nyingĩ rĩu maikare makomete,\n" +
                "Ithuĩ na ithuĩ twĩhokete atĩ no egũcoka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " Ciugo ciake nĩcio tawa na tũtikahĩtithio\n" +
                "Ona mogwati mangĩũka ndagatũrekereria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                " Ciĩko na wĩtĩkio nĩcio ciagũtooria thũ ciitũ\n" +
                "Twathĩka no tũrathimwo twaga kũgĩa na ngaanja.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("92. Huuha Coro Wa Injili (NZK 64)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Huha coro wa Injiri, Iguithia andũ othe,\n" +
                "Nĩguo arĩa megwĩtĩkia, Merire mahonokio.\n" +
                "\n" +
                "\n" +
                "Huha coro wa kwĩhũga, Thĩ nyũmũ na iria-inĩ\n" +
                "Ngai nĩwe ũgwathĩte, nĩguo arĩa ohe mohorwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Anĩrĩra irĩma-inĩ, Miungu-inĩ na werũ-inĩ,\n" +
                "O na iria-inĩ guothe, Nĩmareheirwo muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Anĩrĩra ng’ongo ciothe, Na kũrĩa gũtumanu,\n" +
                "Nĩmathondekeirwo handũ, Ngai nĩametereire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Huha tondũ andũ aingĩ, Nĩmekwenda mohorwo,\n" +
                "Mwathani nĩekũmera, “ũkai kũrĩ niĩ.”\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("93. Nituthiini Ita Thigari Cia Ngai (NZK 65)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩtũthiĩni ita thigari cia Ngai,Jesũ arĩ mbere nĩegũtũtwara\n" +
                "Jesũ nĩarĩkĩtie gũthiĩ mbara-inĩ nĩtũthiĩ kwĩ Jesũ nĩũndũ nĩ wa ma.\n" +
                "\n" +
                "\n" +
                "Nĩtũthiĩni ita andũ a Ngai\n" +
                "Nĩtũrũmĩrĩre, Jesũ arĩ mbere.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Mbũtũ cia shaitani, hĩndĩ ĩrĩa ĩkaigua,rĩĩtwa rĩu rĩa Jesũ nĩ kũũra ikoora\n" +
                "Kenai andũ a Ngai ugĩrĩriai mugĩrĩrie na kayũ na ningĩ na hinya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Gakundi kanini ka andũ a Ngai,ka maithe maitũ igai nĩ riitũ\n" +
                "Tũrĩ rũmwe nao wĩtĩkio nĩ ũmwe,kĩrĩgĩrĩro o kĩmwe ona mwĩhoko.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Mũtiarahũkei andũ a Ngai mũkumiei na ngoro ona mĩgambo\n" +
                "Mũthamaki Jesũ nĩatĩĩo mũno arokumio igũrũ ona thĩ guothe.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("94. Ngoro Yakwa Arahuka (NZK 94)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngoro yakwa arahũka, ũkĩrutanĩrie;\n" +
                "Mwathani akũhotithia, nĩũkaheeo thũũmbĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mũgaambo ũcio wa Ngai, nĩguo ũragwĩta,\n" +
                "Nĩguo agũtanahĩre, na thũũmbĩ ya muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Aira nĩguo maigĩhĩte, arĩa magũkuona;\n" +
                "Thiĩ mbere ndũkehũgũre, rora mbere wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mwathani nĩũtwambĩrĩirie, kĩhĩtahĩtano;\n" +
                "O na ningĩ twahootana, hootani nĩ waku.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("95. Gitina Kirumu Ni Ciugo Cia Ngai (NZK 75)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Gĩtina kĩrũmu nĩ Ciugo cia Ngai,\n" +
                "Nĩmũheetwo cio inyuĩ andũ a Ngai,\n" +
                "Nĩ ũndũ ũngĩ ũrĩkũ Mwathani angĩuga,\n" +
                "Wĩtĩkio ũcio wanyu wongerereka?\n" +
                "Wĩtĩkio ũcio wanyu wongerereka?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mangĩkwĩra ũriinge rũũĩ rũa nduma,\n" +
                "Njũũĩ icio cia guoya ndũgeetigĩra;\n" +
                "Ndĩrĩkoragwo nawe ngũteithagĩrĩrie,\n" +
                "Nĩguo ndũkaamakio nĩ mathĩĩna!\n" +
                "Nĩguo ndũkaamakio nĩ mathĩĩna.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "O na ũngĩgaikio mwaki-inĩ thĩĩnĩ,\n" +
                "Nĩgakũhe hinya wone rĩũrĩro,\n" +
                "Ndũkahĩa no gĩko gĩkaahĩa gĩothe;\n" +
                "Nawe ũtigwo ũhaana ta thahabu,\n" +
                "Nawe ũtigwo ũhaana ta thahabu.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("96. Arahuka Wee Ngoro (NZK 67)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Arahũka wee ngoro, thũ ngiri na ngiri,\n" +
                "Nĩirageria gũkũgũithia, ũguucio meehia-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Arahũka ũhooe, nĩguo ndũkahootwo;\n" +
                "Rũa mbaara hĩndĩ ciothe, hooya Ngai ũteithio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndũrĩ ũrahootana, wĩohe mathaita;\n" +
                "Ndũgatige kũmĩrũa nginya ũheeo thũũmbĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("97. Ningwihoka Thakame Io (NZK 69)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩngwĩhoka thakame ĩyo ya Mwathani wakwa Jesũ\n" +
                "ũthingu ndirĩ mũiganu wa Gũthambia mehia makwa.\n" +
                "\n" +
                "\n" +
                "Ngwĩhanda igũrũ ria Jesũ\n" +
                "Nĩwe rwaro irahĩro\n" +
                "Nĩwe rwaro irahĩro.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Ona rũgendo rwa raiha nowe Mũhonokia wakwa\n" +
                "Ona ndaikanio nĩ ndiihũ ngũnyitwo nĩ hinya wake\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Jesũ nĩwe kĩgoongona ngwihoka thakame yake \n" +
                "Ndanyiihĩrwo nĩ indo cia thĩ Mwathani nĩanjiganĩte.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Ndanyiitwo nĩguo njiirithio, ndĩna thaayũ ngoro-inĩ\n" +
                "Ndahuumbwo ũthingu wa Jesũ, ndikamaka ndĩ harĩ we. \n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("98. Ruagirira Ngai (NZK 68)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Rũagĩrĩra Ngai gũũkũ thĩ ona thĩĩna ũngĩingĩha mũno\n" +
                "Rũaro-inĩ nĩho  ũrĩĩthiaga mehia mangĩritũha.\n" +
                "\n" +
                "\n" +
                "Nĩtũrũũgame wega igũrũ rĩa rwaro,Rwaro nĩ Kristo wiki\n" +
                "Nĩho he thayũ tũngĩrũũgame gĩtĩ-inĩ gĩake kĩa ũnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Rũagĩrĩra o ũhoro wa ma wĩna ngoro theru na wĩtĩkio\n" +
                "Rũaro-inĩ nĩho ũngĩhotera wonje waku mũnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Rũagĩrĩra ũhoro wa ma,ona waikara nĩũkahootana\n" +
                "Rũaro-inĩ niho ũgaakenera mbara ĩno yathira.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("99. Wiigue Uhooe (NZK 71)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Wĩigue ũhoe, Rĩrĩa wĩ na ihinda, Ihinda ti inene, ũkĩra wĩhũge,\n" +
                "Mwĩri ndũrĩ hinya, Thũ iitũ nĩ njamba\n" +
                "Mũhikania nĩegũka, Rĩu e hakuhi.\n" +
                "\n" +
                "\n" +
                "Wĩhũge ũhoe, Wĩhũge, ũhoe,\n" +
                "Wĩigue ũhoe mũthenya na ũtukũ\n" +
                "Ndũkae gũcũnga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Nina toro waku, Nina mathangania,\n" +
                "Nĩwĩrĩirũo kiheo, Gĩkeno matu-inĩ,\n" +
                "Jesũ nĩeiguire, Agĩkũhoera, Athithinire thakame no ũndũ waku\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "ĩtĩkĩra Jesũ, Nowe hinya waku, Wĩyohe mathaita, Thũ ndĩrĩ kũraya\n" +
                "Rĩu hena hingo, Tiga gũte ihinda, Ndũkagonderere, ũkĩra wĩhũũge. \n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("100. Mbaara Ni Njihu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mbaara nĩ njĩhu Thũ ĩĩ hamkuhĩ, Nĩtũrũĩrĩre Jesũ.\n" +
                "Oyai mathaita Na mũũmĩrĩrie, Muuge atĩ Nĩkwandĩkĩtwo.\n" +
                "\n" +
                "\n" +
                "Arahũka ũtarĩ na guoya O kahora mwaranagĩrie,\n" +
                "Menereria wĩra wa shaitani, Kristo nĩwe mũnene witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩtũthiĩ mbere tũtekũmaka tondũ nĩithui tũkũhota\n" +
                "Itimũ na ngo nĩ ũhoro wa ma naguo ndũrĩ hingo ukahotwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mwathani mwega tũthikĩrĩrie tũteithie tondũ wĩ mwega,\n" +
                "Mbaara yathira ũtwamũkĩre ũtũhe mũndũ thũmbĩ yake.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("101. Tugwaka Iguru Ria Ruaro (NZK 72)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũgwaka igũrũ rĩa rũaro, Nĩ Jesũ Rũaro rũa tene;\n" +
                "Nĩtũũmagĩrĩriei ithũũngũthia, kĩhuuhũkanio gĩoka.\n" +
                "\n" +
                "\n" +
                "Tũgwaka igũrũ (Tũgwaka igũrũ rĩa Rũaro)\n" +
                "Tũgwaka igũrũ (Tũgwaka igũrũ rĩa Rũaro)\n" +
                "Tũgwaka igũrũ rĩa Rũaro, igũrũ rĩake Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Angĩ maakaga tĩĩri-inĩ, tĩĩri wa gũũkũ thĩ ĩno;\n" +
                "Nao angĩ mũngũtũko-inĩ, wa ikeno cia mehia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Wee nawe aka hau Rũaro-inĩ; Gĩtina kĩrũmu kĩa ma,\n" +
                "Mwĩhoko waku nĩũgũtũũra, Mwĩhoko wa ũhonokio.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("102. Undoongorie Mwathi Jesu (NZK 73)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "ũndoongorie Mwathi Jesũ, Njerekeire o matu-inĩ,\n" +
                "Ngũkũhooya ngĩthiaga Mwathi njoya ũndongorie.\n" +
                "Mwathi njoya ũndoongorie na wĩtĩkio wa matu-inĩ,\n" +
                "Kũndũ kwega gwĩ gĩkeno, Mwathi njoya ũndongorie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndikwenda njikaranagie, na nganja hamwe naguoya,\n" +
                "Arata menda gũikara Niĩ njoya ndoria gwaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ngwenda ndige kuona thĩ ĩno, Shaitani anjikagia mĩguĩ\n" +
                "Ngũmbũka ndĩ na wĩtĩkio, nyinage ta aria atheru.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ngũmbũka ndorete igũrũ, ũkengi nĩũkũndongoria\n" +
                "No ngĩgakinya Mwathani, Ngũkũhooya ũndũngũrie.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("103. Ta Njiira Wee Muraangiri (NZK 74)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ta njĩĩra we mũraangĩri, Kũrathererũka atĩa!\n" +
                "Riiri ũcio wa Zayuni, kwĩ na marũũri maguo?\n" +
                "Wee Mũgeendi ndũgĩũkĩre, ũrorete matu-inĩ,\n" +
                "Warahũke wĩhotore, Tondũ rĩu nĩkũrakĩa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mũraangĩri kiũmĩrĩrie, nĩũmũrĩkĩirũo njĩra,\n" +
                "Marũũri ma gũũka gwake, Mũthenya wĩ hakuhĩ;\n" +
                "Rĩrĩa coro ũrĩhuuhwo, arĩa akuũ maarahũke,\n" +
                "Andũ a Ngai arĩa atheru, Nĩmakaheeo muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mũrangĩri one ũkeengi, wa mwaka wa Thabatũ;\n" +
                "Mĩgaambo nĩĩranĩrĩra, ũthamaki nĩmũkinyu,\n" +
                "Mũgeendi niĩ nĩndĩroona, Kĩrĩma gĩa Zayuni,\n" +
                "Mũciĩ wa Jerusalemu, O na ũthaka waguo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mũthamaki aikarĩte, mũciĩ ũcio mũthaka,\n" +
                "Gĩtĩ-inĩ gĩake kĩega, Nĩho athamakagĩra,\n" +
                "Kwĩ na thaayũ kũũndũ guothe, o na bũũthi mĩgũũnda-inĩ;\n" +
                "Tĩĩri naguo wĩ na ũnoru, Na njũũĩ nĩihoreire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Mũrangĩri twĩ hakuhĩ, Bũrũri ũcio mũthaka,\n" +
                "Tũthiĩ mbere tũkenete, Bũrũri waa gĩkeno,\n" +
                "Thikĩrĩria kwĩ na rũĩmbo, Rũa arĩa mahonoketio;\n" +
                "Nawe ndũkĩĩrutanĩrie, ũtuĩke wa rũĩmbo rũu\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("104. Inyui Ni Inyui Ene Uthamaki (NZK 76)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Inyuĩ nĩ inyue ene ũthamaki, Mũkomete nginya rĩu nĩkĩ?\n" +
                "Arahũkai mwĩyohe mathaita Na ihenya mũno hingo nĩ thiru.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndũrĩrĩ cia thĩ iria iina hinya, Irenda mbaara o na wanangi\n" +
                "Thikĩrĩria ũigue makaari macio wetereire kĩ ene ũthamaki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mũtikagonderere o na atĩa Riiri wa thĩ no ta kĩruru\n" +
                "Tua nyororo icio Shaitani akuohete, Wetereire kĩ mwene ũthamaki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Roria maitho maku mbere wega, Wone Mũthamaki nĩaroka,\n" +
                "Kũrĩa irĩma-inĩ nĩũkuona ũkengi Kena ũthamaki nĩ waku!\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("105. Muraangiri Uruugamite Ruthiingo-ini (NZK 77)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũraangĩri ũrũũgamĩte,\n" +
                "Rũthiingo-inĩ Zayuni,\n" +
                "Kũhaana atĩa ũtukũ ũyũ?\n" +
                "Rĩũ gũkĩrĩ gũkĩa?\n" +
                "Nĩkũronania gũgĩthererũka?\n" +
                "Nĩkũronania gũgĩthererũka?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tũrĩ rũgeendo iria-inĩ,\n" +
                "Nĩtũroona thĩ nyũmũ?\n" +
                "Tũkũraara iria-inĩ?\n" +
                "Thĩ nyũmũ ĩ kũraihu?\n" +
                "Nĩguo nĩ ma, thamaki nĩmũkinyu?\n" +
                "Nĩguo nĩ ma, thamaki nĩmũkinyu?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩtũroona ũtheri wake,\n" +
                "We njata ya rũciinĩ,\n" +
                "Njata ngeengi, njaru wega,\n" +
                "ĩrakeenga matu-inĩ,\n" +
                "Gĩkenei ‘honokio wĩ hakuhĩ.\n" +
                "Gĩkenei ‘honokio wĩ hakuhĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Twarora mebu njĩra-inĩ,\n" +
                "Twĩ hakuhĩ na thĩ ĩo,\n" +
                "Tũthiĩ mbere na ihenya,\n" +
                "Nĩtũgũkinya igũrũ,\n" +
                "Gĩkenei, na mũine Nyĩmbo cianyu\n" +
                "Gĩkenei, na mũine Nyĩmbo cianyu.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("106. Mwathani Ni Nginya  Ri?");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani nĩ nginya rĩ,\n" +
                "Tũgũgweterea?\n" +
                "Nĩtũnogetio ngoro,\n" +
                "Nĩ gũikara gwaku.\n" +
                "Nĩ hĩndĩ ĩrĩkũ ũgooka,\n" +
                "Nĩguo tũgĩkene?\n" +
                "Nĩ ũndũ wa gũũka gwaku,\n" +
                "Na ũnene mũingĩ?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mwathani nĩ nginya rĩ,\n" +
                "ũgũtiganĩria?\n" +
                "Andũ arĩa wakũũrire,\n" +
                "Maikare na ngaanja?\n" +
                "Nĩ anyinyi metĩkĩtie,\n" +
                "Atĩ nĩũgũcoka;\n" +
                "Nĩ anyinyi meharĩirie,\n" +
                "Gũthagaana Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Arahũra andũ aku:\n" +
                "ũgĩrĩria mũno;\n" +
                "Tuĩkai andũ atheru,\n" +
                "Jesũ nĩegũcooka!\n" +
                "Nĩ hĩndĩ ĩrĩkũ ũgooka,\n" +
                "Nĩgeetha tũkene,\n" +
                "Rĩrĩa ũgaikũrũka,\n" +
                "Na ũnene mũingĩ?\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("107. Mwaki Uria Wakanaga (NZK 80)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwaki uria wakanaga ngoro-inĩ cia andũ arĩa atheru,\n" +
                "Wiha rĩu wakanage Thĩini witũ kwagia thĩĩna.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t2.\n" +
                " Roho wa Aburahamu  eha Mwĩkanĩri rũũri rũaku,\n" +
                "Nĩororoirie Paul Akĩmũhe hinya mwerũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " Roho ũcio nĩ wa tene, Nĩ mũrutani wa wendo,\n" +
                "Nĩateithĩrĩirie Isaia, Na agĩtongoria Daudi.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Wega waku wĩha rĩu, ũrĩa wonirie Elija,\n" +
                "Musa agĩkenga ta riũa, Ayubu akĩũmĩrĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\n" +
                " Ririkana tene tene Werũhie wĩra waku Ngai,\n" +
                "Twahingũra ngoro ciitũ, Tũitĩrĩrie Roho waku.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("108. Atheru Ukirai");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Atheru ũkĩrai, Ita cia Mwathani,\n" +
                "Haicia bendera iitũ, Mwĩhaarĩrie mũno,\n" +
                "Twathiĩ nake mbaara-inĩ, No tũkahootana,\n" +
                "shaitani nĩakahootwo, Nĩ Mwathani Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Atheru ũkĩrai, Thongori nĩhuuhe,\n" +
                "Umai na ihenya, Tũthiĩ mbaara-inĩ,\n" +
                "Reke tũthiĩ na Jesũ, Tondũ thũ nĩ nyingĩ,\n" +
                "Twathiĩ hamwe na Jesũ, No tũkahootana.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Atheru ũkĩrai, Njaamba cia Mwathani,\n" +
                "Hinya wanyu wa mwĩrĩ, Mũtikawĩhoke,\n" +
                "Wĩhokagei ciugo, cia Ibuku rĩa Ngai,\n" +
                "Mwĩtĩkie ũhoro wa ma, Na kũhooya mũno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Atheru ũkĩrai, Thigari cia Ngai,\n" +
                "Mbaara nĩ ndũrũ mũno, Rũĩrai Jesũ,\n" +
                "Aingĩ nĩmamũthũire, Matimwĩtĩkagia,\n" +
                "Twathiĩ hamwe na Jesũ, No tũkahootana.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("109. Ngwenda Witikio Murumu (NZK 79)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngwenda wĩtĩkio mũrũmu ũtangĩhooteka,\n" +
                "Wa gũtooria ĩngĩkiara Ndoragie thũ ciothe,\n" +
                "Wa gũtooria ndakĩara\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngwenda wĩtĩkio mũrũmu Na ti wa mateta,\n" +
                "Ndona thĩna ndona ruo Ndĩĩhokage Ngai,\n" +
                "Ndona thĩna na ruo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "ũũru woka ndikamake, ũthiũ ũgakenga\n" +
                "Njage guoya njage nganja, ũgwati ũngĩũka,\n" +
                "Njage guoya na nganja.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("110. Gitina Gia Kanitha (NZK 195)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Gĩtina gĩa kanitha, Nĩ Mwathani witũ;\n" +
                "Nake nĩwe wokire, akiuma Igũrũ;\n" +
                "Oke gũũkũ acarie, Andũ arĩa morĩĩte,\n" +
                "Nĩgeetha amagũre, na thakame yake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kanitha ũcio ũmwe, wa kĩrĩĩndĩ gĩothe,\n" +
                "Gĩa thĩ na kĩa Igũrũ, Na njĩra no ĩmwe,\n" +
                "Irio ciao nĩ imwe, Wĩtĩko no ũmwe,\n" +
                "Na andũ aguo othe magoocaga Kristo.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Kanitha nĩũmenagũo, ũgathĩnio mũno,\n" +
                "Na ningĩ ũgathũũkio, Nĩ amwe arĩa aguo;\n" +
                "No othe arĩa atheru, Mahooyaga Kristo,\n" +
                "ũtukũ ũyũ ũthire, gũthere kĩrooko.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Kanitha nĩũkenaga, Ningĩ wĩ na thaayũ,\n" +
                "Hatigairie hanini, Thĩna ũgaathira,\n" +
                "Atheru mahootana, Makahuurũkio ma.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("111. Aria Mendeete Ngai (NZK 70)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Arĩa mendete Ngai ũkani tũkene,\n" +
                "Tũine nyĩmbo cia gĩkeno, tũine nyĩmbo cia gĩkeno,\n" +
                "Nĩtũgathe Ngai, nĩtũgathe Ngai.\n" +
                "Tũrathiĩ Sayuni, nĩkwega mũno Sayuni,\n" +
                "Tũrathiĩ Igũrũ Sayuni, Nĩkuo Ngai atũũraga.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩtũkũigua ngoro-inĩ nĩtũrathimĩtwo,\n" +
                "Tũgĩgakinya matu-inĩ, Tũgĩgakinya matu-inĩ,\n" +
                "Kũria kwega mũno kũria kwega mũno.\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Arĩa matetĩkĩtie matihota kũina,\n" +
                "Arĩa metĩkĩtie noo, Arĩa metĩkĩtie noo,\n" +
                "Mainaga gũkũ thĩ, mainaga gũkũ thĩ.\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Hĩndĩ ĩrĩa tũkamuona thĩna nĩũgathira,\n" +
                "Tũkanyua maĩ ma muoyo, tũkanyua maĩ ma muoyo,\n" +
                "Na tũkene mũno na tũkene mũno.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("112. Mithenya Itaandatu Riu (NZK 81)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mĩthenya ĩtandatũ rĩu nĩ mĩthiru Thabatũ ĩgooka\n" +
                "Ngooro yakwa gĩkenere mũthenya ũyũ wa Ngai witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kumia Ngai ũrĩa ũheete arĩa anogu kĩhurũko,\n" +
                "Kũhuurũka ngoro ciao Gũkĩra mĩthenya ĩrĩa ĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ngoro ciitũ nĩũkenaga ona igĩcokagia ngatho\n" +
                "Ciiyũrĩtio gĩkeno kĩu gĩa gũkĩra ikeno ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\tGĩkeno kĩu kĩa ngoro nĩ rũũri rũa kĩhuurũko\n" +
                "Kĩrĩa kĩigitwo\n" +
                " matu-inĩ, gĩakũnina thĩna wothe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("113. Muthenya Mutheru (NZK 87)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Muthenya mutheru, ucio wa Thahabu,\n" +
                "Andu aku Ngai witu, nĩmamwendeete ma.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩwautheririe, ukĩutaathima,\n" +
                "Muthenya ucio wa Thabatu, nĩ waku Mwathani.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Na ithuĩ turaathime, tugĩkuhooya rĩu,\n" +
                "Muthenya ucio wa gĩkeno, nĩ waku Mwathani.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ningĩ hamwe nawe, kuu gwaku matu-inĩ,\n" +
                "Tukwenda tukahuuruka, Thabatu yaku Ngai.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("114. Wee Ririkanaga Thabatu (NZK 84)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Wee ririkana, thabatũ ya Ngai\n" +
                "Nĩguo mũthenya mwega, Gũkĩra ĩrĩa ĩngĩ\n" +
                "ũrehage gĩkeno, O na ũhurũko,\n" +
                "ũkengi waguo wothe, Nĩ ũnene wa Ngai.\n" +
                "\n" +
                "\n" +
                "ũka rĩu, ũka rĩu, Thabatũ ndathime,\n" +
                "Kuhĩrĩria, kuhĩrĩria, Thabatũ njega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\tt2. \n" +
                "ũtue nĩ Mũtheru, ũmũhoe, Rĩu,\n" +
                "ũrĩa werire arutwo, Niĩ nĩ niĩ njĩra,\n" +
                "Na tũngĩrũmĩrĩra, Mũhonokia gũkũ,\n" +
                "Nĩtũkaheo muoyo, Wa tene na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Mũthenya mũtheru, Tũũhũthĩre wothe\n" +
                "Tũkĩinagĩra Jesũ, Mũrata wa andũ othe,\n" +
                "Mũhonokia mwega ma, Wĩ mwega makĩria,\n" +
                "ũtũũrage ngoro-inĩ, Ciitũ hingo ciothe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("115. Ndi Na Mukuuri Uhaithanagira");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndĩ na Mũkũũri ũthaithanagĩra,\n" +
                "Mũkũũri mwendani aingĩ matiũĩ,\n" +
                "Na rĩu nĩandorete e na wendo mũingĩ,\n" +
                "Ndakena angĩtuĩka Mũkũũri waku.\n" +
                "\n" +
                "\n" +
                "Nĩngũhooyagĩra, Nĩngũhooyagĩra,\n" +
                "Nĩngũhooyagĩra, mũrata wakwa\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndĩ na baba wakwa ũrĩa ũheete mwĩhoko,\n" +
                "Wa muoyo ũrĩa ũtagaathira,\n" +
                "Nĩaranjĩta narua tũcemanie nake,\n" +
                "Ndakena angĩtwĩta twĩ hamwe nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndĩ na nguo njega njerũ ya matu-inĩ\n" +
                "ĩnjetereire thiĩ ngamĩhuumbe, kuo,\n" +
                "Hihi ngahaana atĩa ndarĩĩkia kwĩhuumba,\n" +
                "Nĩtũgathiĩ nawe ũkehuumbe yaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndĩ na thaayũ mwega ũhaana ta rũũĩ,\n" +
                "Thaayũ andũ a thĩ ĩno matangĩmenya,\n" +
                "Mũheani na mwene guo nĩ Mwathani,\n" +
                "Ndaakena ingĩmenya nawe wĩ na guo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Wamenyana na Jesũ ĩra andũ arĩa angĩ,\n" +
                "Mũhonokia wakwa atuĩke waku,\n" +
                "Mahooere nĩguo o nao maheeo,\n" +
                "Riiri ũcio wake wamahooera.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("116. Muthenya Wa Gikeno (NZK 82)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Muthenya wa gĩkeno, Muthenya Mutheru,\n" +
                "Ithuĩ nĩtukenaga; tugĩuka kuhooya;\n" +
                "Um ‘uthĩ andu a Ngai, anyinyi na anene;\n" +
                "Nĩmamukuhĩrĩirie nĩguo maraathimwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Umuthĩ, nĩ Thabatu, muthenya urĩa mwega;\n" +
                "Uhaana ta muguunda, u yuire iraathimo.\n" +
                "Na rĩrĩa tungĩnyota, no tukanyua maaĩ,\n" +
                "Ma karuuĩ gatheru kahehete wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Namo meciiria maitu, marorage iguru,\n" +
                "Nĩtutige meciiria, marĩa ma guuku thĩ,\n" +
                "Rĩu nĩtwĩgagure, irio cia matu-inĩ,\n" +
                "Na tugĩkenagĩra maundu ma Ngai.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("117. Haria Andu Mahuurukaga (NZK 83)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Harĩa andũ mahuurũkaga Thabatũ ya Ngai\n" +
                "Ngoro yakwa ĩnguucagia Thiage harĩ Ngai\n" +
                "Mũthenya…. Mwega ma\n" +
                "Mũthenya…. Mwega ma\n" +
                "Ngoro yakwa ĩnguucagia\n" +
                "Huurũke Thabatũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngoro yakwa nĩkenaga Hingo ya Thabatũ\n" +
                "Ningĩ nĩnyonaga Jesũ Ndakinya harĩ we.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ningĩ Jesũ ni Mwendani Anguucagia wega,\n" +
                "Nĩgetha ndĩmũhe ngoro, Thiage harĩ we.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("118. Kiumia Kiingi Kigima Nigiathira (NZK 85)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kiumia kĩĩngĩ kĩgima, nĩgĩathira na thaayũ.\n" +
                "Nĩtũthiĩni kwĩ Jesũ, nĩguo atũraathime;\n" +
                "Mũthĩ ũyũ wa Thabatũ, mũthenya mwega mũno;\n" +
                "Mũthĩ ũyũ wa Thabatũ, mũthenya mwega mũno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũmũũthĩ tũhe ũtheri, kuuma gwaku igũrũ,\n" +
                "Eheria meehia maitũ, twamũkĩre na wendo,\n" +
                "Mooko nĩmahuurũke, tũtũũre thĩinĩ waku;\n" +
                "Mooko nĩmahuuruke, tũtũũre thĩinĩ waku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũũnganĩĩte haha, tũgooce rĩĩtwa rĩaku.\n" +
                "Thengerera harĩ ithuĩ, ũtũhe wega waku;\n" +
                "Tũcamĩrwo nĩ ciugo, nginya tene na tene,\n" +
                "Tũcamĩrwo nĩ ciugo, nginya tene na tene,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "ũmũũthĩ ithuĩ eehia , twĩtĩkie ũhoro wa Ngai,\n" +
                "ũtuongerere hinya, O na ũtũhonagie,\n" +
                "ũtũkenagia ngoro, ũtũhũũnagia irio;\n" +
                "ũtũkenagia ngoro, ũtũhũũnagia irio;\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("119. Muthenya Uyu Wa Thabatu (NZK 88)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũthenya ũyũ wa Thabatũ, Nĩ wega gwĩciiria,\n" +
                "Maũndũ na Ngai, na gũtiga marĩa ma thĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩ mwega tũkĩigua kiugo, Kuuma kwĩ mũhuunjia,\n" +
                "ũrĩa ũrarutana, twĩrire tũgie na muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Twĩ mbaara-inĩ ya meehia, Tũngĩaga kũhootwo,\n" +
                "We wonaga ngoro, We no atũhootanĩre.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("120. Thabatu Niyakinya (NZK 86)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Thabatũ nĩyakinya, mũthenya wa gĩkeno,\n" +
                "Ngoro yakwa kenera, ũtheri wa matu-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngoro nĩĩhoreragio, kĩhuurũko-inĩ gĩĩkĩ,\n" +
                "Tũninagĩrũo thĩĩna, nginya tũkinye mũciĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũkumagia Mwathani, hamwe na kũmũhooya,\n" +
                "Kwamũkĩra, irathimo, mũthenya ũyũ mũtheru.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("121. Mugaambo Wakwa Ruciini (NZK 121)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũgambo wakwa rũcinĩ nĩũrĩkayagĩra\n" +
                "Mwathani wakwa ngĩhooya nĩguo andaathime.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Ngũkũhoya Roho waku nĩguo andoongorie\n" +
                "Njĩkage ũrĩa njagĩrĩirwo, na ũrĩa wendaga\n" +
                "\n" +
                "3\n" +
                ".Arĩa makwĩhokete ma, wee nĩũmarĩithagia \n" +
                "Tondũ nĩwe mehokete, nĩmahingagĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Tondũ wa tha ciaku Ngai, nĩngoka harĩ we\n" +
                "Nĩ ngatũũra ngũinagĩra na ngĩkũhooyaga.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("122. Mwathani Wakwa Unjikarie (NZK 91)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani wakwa ũnjikarie tondũ rĩu gukirie gũtuka \n" +
                "Nĩmbatairio nĩ ũteithio waku tondũ rĩu ndĩ nyiki ũnjikarie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Matukũ maitũ nĩ mathiraga tondũ ũcio gũtirĩ gĩkeno\n" +
                "Maũndũ mothe nĩ mathiraga,we mũtũũra muoyo ũnjikarie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Nĩmbatairio nĩwe hiingo ciothe gũtirĩ ũngĩ ũnjiganĩte\n" +
                "Nũũ ũngĩhota kũndongooria, we Mwathani wakwa ũndoongorie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Gũtirĩ ũgwati ingĩtigĩra omarĩa mothe marĩngoraga,\n" +
                " O na kĩo gĩkuũ ndingĩtigĩra nĩũndoranĩirie ũnjikarie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\n" +
                "Rĩrĩa ngomete tũrĩ onawe, ũtheri waku ũmũrikiire\n" +
                "ũtheri wa igũrũ ndũnyihiire matukũ-ini makwa ũnjikarie. \n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("123. Riu Nikuraatuka (NK 92)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Rĩu nĩkuratuka, Andu othe mahuruke,\n" +
                "Nĩtugucokia ngatho\n" +
                "Tuona njata na mweri, ikengete.\n" +
                "\n" +
                "\n" +
                "Ngai wĩ Mũtheru, Wĩ Mũnene,\n" +
                "Wathaga kũndũ guothe,\n" +
                "Thĩ-ĩno ona matu-inĩ, Wĩ Mũtheru\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.  \n" +
                "We Mũgai wa muoyo, Wathaga kũndũ guothe\n" +
                "Tũmenyerere wega, Tondũ We wĩ hakuhĩ\n" +
                "Nginya gũkĩe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Wendo waku mũnene, ũtũikũrũkĩre,\n" +
                "Twarĩrie wega waku, Ta njata cĩa matu-inĩ\n" +
                "Nginya gũkĩe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Na rĩrĩa ũgoka thĩ, ũtũtware Igũrũ\n" +
                "We Ngai wa araika, Tũgatũũrania nawe\n" +
                "Nginya tene.\n" +
                "\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("124. Utheri Wa Ngoro (NZK 93)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Utheri wa ngoro yakwa, Muhonokia mwendani wakwa,\n" +
                "Nduma ya utuku gutirĩ, Ungĩkoruo hamwe na niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "O na ingĩkoma urĩrĩ, Njiyuria meciiria maku,\n" +
                "Nĩ wega muno ngĩkoma, wendo-inĩ utarĩ muthia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Unjikaragie Mwathani, Utuku o na muthenya,\n" +
                "Ingĩikara hamwe nawe, nĩguo ingĩtuura muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Haana kaana ndirĩ hinya, Nguucĩrĩirio ngagutiga,\n" +
                "Wĩcarĩrie mwana waku, Unjokie wendo-inĩ waku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Thaayu waku uikaragie, Arĩa aruaru na athĩĩni,\n" +
                "Rĩrĩa maakaya nĩ ruo, Mahoorerie mahe toro.\n" +
                "\n" +
                "6.\t\n" +
                "Rĩrĩa twokĩra ruciinĩ, Uka hakuhĩ na ithuĩ,\n" +
                "Twatoonya mawĩra ma thĩ, Tuikare mooko-inĩ maku.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("125. Kuundu Guothe Miguunda-ini (NZK 94)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kũũndũ  guothe mĩgũnda-inĩ ĩiyurĩte magetha\n" +
                "Mĩkuru-inĩ na cianda-inĩ magetha no gũkenga.\n" +
                "\n" +
                "\n" +
                "Mwene magetha nĩ wega ũkĩrehe agethi\n" +
                "Monganie magetha maku nĩguo wĩra ũthire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mamatware kwĩ rũci-inĩ o na riũa rĩarĩte\n" +
                "O kinya riũa rĩthũe makĩũngania magetha.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Inyuĩ agethi a Mwathani rehei magetha rĩu,\n" +
                "Ningĩ hwaĩ-inĩ mũtonye gwake mũkenete ma.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("126. Iguai Uhoro Wa Mwathani (NZK 95)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Iguai ũhoro, wa Mwathani witũ, Mũrehe gĩcunjĩ mũthithũ-inĩ,\n" +
                "Mũrehe na ngoro cianyu ciothe, Nĩguo kĩrathimo gĩũke.\n" +
                "\n" +
                "\n" +
                "Rehei gĩcunjĩ mũthithũ-inĩ, Na mũkĩngerie rĩu nakio;\n" +
                "Na nĩngũmũhe kĩrathimo,\n" +
                "Nginya mwage gwa kũiga nĩ kũingĩha.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩũkwenda kũheo Roho Mũtheru? No kĩrehe gĩcunjĩ ikũmbĩ- inĩ,\n" +
                "ũikare hakuhĩ na Ngai waku, Nĩguo ũrĩrathimagwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "ũrĩ na ihĩtia na Mwathani waku? ũkĩrehe gĩcunjĩ ikũmbĩ-inĩ,\n" +
                "ũrehe gĩcunjĩ mũthithũ-inĩ, nĩguo ũrĩrathimagwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Gocaga Mwathanĩ na ngoro yothe, ũkĩrehe gĩcunjĩ ikũmbĩ-inĩ,\n" +
                "Wĩtĩkie ciĩranĩro ciake ciothe, Nĩguo ũrĩrathimagwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Tũine ithuothe nyĩmbo cia gĩkeno, Tukĩrehe gĩcunjĩ ikũmbĩ-inĩ,\n" +
                "Tũine na gĩkeno kĩnene mũno, Tondũ nĩ tũkũrathimwo.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("127. Nitwagucookeria Indo Ciaku (NZK 96)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩtwagũcookeria, Indo ciaku We Ngai,\n" +
                "Iria twĩ nacio nĩ ciaku, Nĩwe ũtũheete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ithuĩ twĩ nduungata, Nĩtũgarũrũke;\n" +
                "Nĩguo tũcookie na ngaatho, Kĩrĩa gĩ kĩa Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "ũtũhe Wendani, wa gũteithanagia,\n" +
                "Nĩguo tũteithagie andũ, Arĩa me nduma-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩtwĩhoke Kiugo, O na gũgĩĩtĩkia,\n" +
                "ũrĩa wothe tũrĩĩkaga, Tũkũgoocithagie.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("128. Mwathani Ngai Ningugega O Muno");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwaathani Ngai, nĩngũgega o mũno,\n" +
                "Rĩrĩa ngwĩciiria ũhoro wa ciũmbe.\n" +
                "Njata, Heni na ciũmbe iria ingĩ ciothe,\n" +
                "ũrĩa ciũmbĩtwo na ũũgĩ waku.\n" +
                "\n" +
                "\n" +
                "Reke ngoro yakwa ĩkũinĩre,\n" +
                "Nĩũndũ wee wĩ mũnene,\n" +
                "Reke ngoro yakwa ĩkũinĩre,\n" +
                "Nĩũndũ we wĩ Mũnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Rĩrĩa ngũceera gũũkũ guothe thĩ ĩno,\n" +
                "Nĩnjiguaga nyoni ikĩgaamba,\n" +
                "Irĩma nacio nĩingenagia mũno,\n" +
                "O na rũhuuho no rũngenagia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Rĩrĩa ngwĩciiria ũhoro waku Ngai,\n" +
                "ũrĩa Watũmire Mwana-waku,\n" +
                "Ooke akue nĩũndũ wa meehia maitũ,\n" +
                "ũndũ ũcio wĩ hinya kũũmenya.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Jesũ Mũhonokia rĩrĩa ũgooka,\n" +
                "Kũnjoya nĩguo ũndware Igũrũ,\n" +
                "Nĩngakũgoocaga tene ona tene,\n" +
                "Andũ othe mamenye ũrĩa ũtariĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("129. Nguthaambio Meehia Ni Kii? (NZK 97)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngũthambio mehia nĩkĩ? Nĩ thakame yake Jesũ\n" +
                "Ngai nĩakenaga nĩ Thakame yake Jesũ\n" +
                "\n" +
                "\n" +
                "Gũtirĩ mũthaiga ũngĩthambia mehia\n" +
                "Kana ũngĩtheria, no thakame yake Jesũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Gũtirĩ kĩngĩtheria no thakame yake Jesũ\n" +
                "  Kana kũniina mehia, no thakame yake Jesũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Gũtirĩ mũiguithania no thakame yake Jesũ\n" +
                "Nĩngũmakio nĩ ciira ingĩaga Mwathani Jesũ \n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Hatirĩ mwĩhoko no thakame yake Jesũ\n" +
                "Gũtingĩoneka thayũ no thakame yake Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\n" +
                "Thĩ yothe nĩĩhotetwo nĩ thakame yake Jesũ\n" +
                "Nĩtũgakinyio igũrũ, nĩ thakame yake Jesũ\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("130. Aria Moorite Mimagacario (NZK 100)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Aria morite, nimagacario, kũu irĩma-inĩ maracanga\n" +
                "Merũo ‘ũkai’, Moke kwĩ Jesũ, Nĩguo ũmũthĩ ekũmera.\n" +
                "\n" +
                "(Basi) Nĩ niĩ ngũthiĩ … na nĩngũmarehe,\n" +
                "Nĩ niĩ ngũthiĩ, thiĩ na nĩngũmarehe,\n" +
                "(Basi) Ndĩmarehe .. marehwo kwĩ Jesũ\n" +
                "Ndĩmarehe, ndĩmarehe kwĩ Jesũ.\n" +
                "(Basi) Moke kiugũ … kĩa Mũmakũũri,\n" +
                "Moke kiugũ … kĩa Mũmakũũri,\n" +
                "(Basi) Gatũrũme … karĩa gaathĩnjirũo.\n" +
                "Karĩa gaathĩnjĩirũo andũ ehia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Arĩa morĩte, nĩmagacario, Macokerio harĩ Jesũ,\n" +
                "Mũmonie njĩra ya ũhonokio, Mone muoyo ũtagathira.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩngũthiĩ tondũ We nĩanjĩtĩte, Ndĩmume thutha hingo ciothe,\n" +
                "Arĩa marakua, na arĩa magwĩte, Moke kwĩ Jesũ nĩwe njĩra.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("131. Niugwitwo Ni Jesu (NZK 101)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩũgwĩtwo nĩ Jesũ ũtuĩke wake, Nĩaragwĩta ũka rĩu,\n" +
                "Wahĩnda nĩkĩ agĩgwĩta, Menya atĩ nĩũrĩĩte.\n" +
                "\n" +
                "\n" +
                "Ndũkĩmũigue, Ndũkĩmũigue\n" +
                "Jesũ nĩaragwĩta wĩheane oro rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Arĩa anogu no mahuurũkio, ũka rĩu ũka rĩu,\n" +
                "Arĩa me na mĩrigo, na kĩeha, magĩe na ũhuurũko.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩagwetereire orĩa ũrĩ, ũke o rĩu o rĩu,\n" +
                "Nawe wĩhĩtie, tiga kwĩhitha: ‘thaambio, ũhuumbwo nguo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Jesũ arathaitha agoondereri, mooke rĩu, mooke rĩu,\n" +
                "Arĩa etĩkia nĩ magakena, Ndũkĩre, narua, ũke.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("132. Mugeni E Muromo-ini (NZK 192)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũgeni e mũromo-inĩ Mũgeni!\n" +
                "Nĩarũũngiĩ o ho mũno Mũgeni!\n" +
                "Ndũmũnyiite ũgeni\n" +
                "Ndũhiingũrĩre Jesũ\n" +
                "Wa ithe witũ mwendani; Mũgeni!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "He Jesũ ngoro yaku Hiingũra\n" +
                "Nĩguo ndagagũtige Hiingũra\n" +
                "Ndwĩtĩkie Mũraataguo\n" +
                "Roho akũhooreragie\n" +
                "Akũmenyagĩrĩre; Hiingũra\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndũgĩkĩigue mũgaambo Wa Jesũ\n" +
                "Na ũthuure maũndũ Ma Jesũ\n" +
                "Kĩhingũre mũrango\n" +
                "Ndũkanamwĩre aca\n" +
                "Etagwo Wa kwĩhokwo Nĩ Jesũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndũkĩhiingũre ngoro, Kwĩ Jesũ\n" +
                "Nĩũkũheeo ũteithio Wa Jesũ\n" +
                "Nĩũkaahuumbwo ũthiingu\n" +
                "Nĩagakũohera meehia,\n" +
                "ũngĩhiingũra ngoro, Kwĩ Jesũ\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("133. Jesu Aratwita Nohooreri (NZK 103)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ aratwĩta nohooreri mũingĩ, (ratwĩta) wee o na niĩ\n" +
                "Atwetereire o ngoro-inĩ ciitũ, Wee nĩagwetereire.\n" +
                "\n" +
                "\n" +
                "“ũkaai kũrĩ niĩ, inyuĩ mũnogeete, ũkaai”\n" +
                "Jesũ aratwĩta nohooreri mũingĩ, Nĩaratwĩta “ũka gwakwa”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Rĩrĩa egũtũhooera ndũkaarege, Nĩ ithuĩ arahooera,\n" +
                "Ndũkamene wega na tha cia Jesũ, Nĩatũiguagĩra tha.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Matukũ nĩmaraathira na ihenya, Na nĩ maku na maakwa,\n" +
                "Gĩkuũ no kĩrooka hingo O hingo, kũrĩ we na kũrĩ niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Wĩciirie ũhoro wa wendo wake, Kũrĩ we na kũrĩ niĩ,\n" +
                "Nĩarĩkĩtie gũtũkirĩra meehia, Ithuĩ nĩ ithuĩ tuoheirwo.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("134. Mbuku Ya Ngai Ni Theru");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mbuku ya Ngai nĩ theru, ĩndeithagia gũthiinga,\n" +
                "Nĩyo ũtheri wakwa, Ngĩthiĩ O handũ hoothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                " Nĩĩnyonagĩrĩria, Nĩ Ngai wanyũmbire,\n" +
                "Ngĩcooka meehia-inĩ, Ndĩ kĩonje, ndĩ mũthĩĩni.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩyo ĩkanyonia ciira, Witũ ithuĩ andũ athĩ,\n" +
                "Ningĩ rĩrĩa tworĩĩte, Mwathani agĩtũkuĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Wendani wa Mwathani, Tũwonaga mbuku ĩno,\n" +
                "We nĩaarutire muoyo, nĩguo atũhonokie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Mbuku ĩno no ĩndaaraga, ũrĩa ndĩrĩthiaga,\n" +
                "Rĩrĩa ndĩ gũũkũ thĩ ĩno, Nĩguo nyedage Jesu.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("135. Muriithi Witu Mwega (NZK 105)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũrĩithi witũ mwega, tũragwĩta ũũke,\n" +
                "Kiugũ-inĩ kĩu gĩake, hatarĩ na ũgwati,\n" +
                "Atumia o na arũme, o na ciana ciothe,\n" +
                "Jesũ mũhonokania, aratwĩta gwake.\n" +
                "\n" +
                "\n" +
                "Aretana e na tha ngoro-inĩ, We ũũrĩte ũka kũrĩ niĩ,\n" +
                "Mwathani Jesũ Mũrĩithi wakwa, aikaire agwetereire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩarutire muoyo, Nĩũndũ witũ ithuothe,\n" +
                "Nĩekwenda arĩa moorĩte, mooke kũrĩ we rĩu,\n" +
                "Tũtige gwĩtigĩra, Gwake kwĩ na thaayũ,\n" +
                "Igua mũgaambo Wake, Nĩwe mũrĩithi witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũtikagonderere, thũ itũ shaitani,\n" +
                "Ahaana o ta njũũi, na nĩegũtwananga,\n" +
                "Nĩtũreetwo nĩ Jesũ, Mũhonokia witũ,\n" +
                "Nĩtũtoonye kiugũ-inĩ, rĩu kwĩ na mweke.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("136. Jesu Riu Niaretana (NZK 107)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.  \n" +
                "Jesũ rĩu nĩaretana, “Nũ ũkũndeithia wĩra?”\n" +
                "Magetha rĩu nĩ maingi, Nũ ũgũthiĩ kũũngania,\n" +
                "Igua kayũ nĩaretana, nĩũrĩheo kĩheo,\n" +
                "Nũ ũkũmũcokeria oige, “Mwathani nĩ niĩ ngũthiĩ.”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "ũngĩaga kũringa iria, waaga gũthiĩ kũraya,\n" +
                "Ruta wĩra kũũ kwanyu, kũũ itũũra-inĩ rĩanyu.\n" +
                "Waaga kwaria ta araika, kana hihi Paulo,\n" +
                "Onia andũ wendo wa Jesũ, Meere nĩamakuĩrĩire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Waaga kũheo gĩtĩyo, ũngĩaga gũtũgĩrio,\n" +
                "Ta andũ arĩa maheeto wĩra, Monagie andũ njĩra;\n" +
                "Wahota kũhoya mũno, na ũtanahe mũno,\n" +
                "Tuĩka ta Harũni mwega, nĩateithĩrĩiria Musa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                " Rĩrĩa Mwathani aragwĩta andũ no marathira,\n" +
                "Ndũkamũcokerie uuge, “Ndirĩ ũndũ ingihota”\n" +
                "Nyita wĩra na gĩkeno, kenera wĩra wake,\n" +
                "Agwĩta wĩtĩke narua, “Ndĩ haha Jesũ ndũma.”\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("137. Nituiguite Uhoro (NZK 108)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩtũiguĩte ũhoro, Jesũ nĩ Mũkũũri,\n" +
                "Huunjiai kũũndũ guothe, Jesũ nĩ Mũkũũri,\n" +
                "ĩrai andũ othe, ũrĩa Jesũ ekire,\n" +
                "Nĩguo maigue meerire, Jesũ nĩ Mũkũũri.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Inai rũĩmbo rwega, Jesũ nĩ Mũkũũri,\n" +
                "Nĩakuire mũtĩ-inĩ, Jesũ nĩ Mũkũũri,\n" +
                "Inai mwĩ thĩĩna-inĩ, Na rĩrĩa mwĩ na kĩeha,\n" +
                "O nahĩndĩ ya gĩkuũ, Jesũ nĩ Mũkũũri.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndũũrĩrĩ nĩciine, Jesũ nĩ Mũkũũri,\n" +
                "Andũ othe metĩkie, Jesũ nĩ Mũkũũri,\n" +
                "Icĩgĩrĩra ciine, Na makũũmbĩ ma iria,\n" +
                "Na thĩ nĩĩkũngũĩre, Jesũ nĩ Mũkũũri.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Rũhuuho nĩrũhuunjie, Jesũ nĩ Mũkũũri,\n" +
                "Ndũũrĩrĩ nĩciine, Jesũ nĩ Mũkũũri,\n" +
                "Cianda-inĩ na irĩma-inĩ, Mũgaambo nĩũiguwo,\n" +
                "Wa rũĩmbo rũa ahootani, Jesũ nĩ Mũkũũri.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("138. Mundu Aigua Nake Niamukirie (NZK 109)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũndũ aigua nake nĩamũkĩrie, Hunjia thĩ yothe harĩa yakinya,\n" +
                "ĩria andũ othe gĩkeno gĩkĩ, ũrĩa ũkwenda nĩoke.\n" +
                "\n" +
                "\n" +
                "ũrĩa ũkwenda, ũrĩa ũkwenda, Anĩrĩra wega ng’ongo-inĩ ciothe\n" +
                "Ithe witũ ekuuga nĩwe Mũgaĩ, ũrĩa ũkwenda nĩoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "ũrĩa wothe mwendi ndagaikare, Nĩ tondũ mũrango nĩmũhingũre\n" +
                "Atonye kwĩ Jesũ rũĩ rũ muoyo, ũrĩa ũkwenda nĩoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " ũrĩa wothe mwendi erĩĩrũo muoyo, Angĩkirĩrĩria magerio wega,\n" +
                "Gũtĩrĩ kĩgiria kĩa ahingĩrio, ũrĩa ũkwenda nĩoke.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("139. Muraango Nimuhiingure Nguona (NZK 110)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũrango nĩ mũhingũre, Nguona ũkengi mwega,\n" +
                "Uumĩte mũtharaba-inĩ, ũkandoria nĩ wendo.\n" +
                "ũi, ũi tha cia Jesũ!, rĩu ndĩmũhingũrĩre,\n" +
                "Hihi nĩniĩ ma? Hingũrĩirũio ũguo?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mwendi gũtonya nĩatonye ũhingũrĩtwo wothe,\n" +
                "Mũthĩni o na gĩtonga O na ndũrĩrĩ ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Thũ ciakũnyiira thiĩ mbere Tha ciake nĩigũthira,\n" +
                "Kuua mũtharaba waku, Nginya ũheo thũmbĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Twakinya kũu mũrĩmo Tũige mĩtharaba thĩ,\n" +
                "Tuoe thũmbĩ tũciĩkĩre Twendage Mũũmbi witũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("140. Murigiti Munene (NZK 111)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũrigiti mũnene ma nĩwe Jesũ mũigũa tha\n" +
                "Ona nĩatũhoreragia, Jesũ mũtũhonokia.\n" +
                "\n" +
                "\n" +
                "Araika gocai Jesũ mũhonokia.\n" +
                "Gũtirĩ rĩĩtwa rĩega gũkĩra rĩake Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Nĩtũkumie gatũrũme,na nĩwe mũhonokia,\n" +
                "Mehia mothe na mahĩtia,Jesũ nĩamatheragia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Ngumo ciothe na rũgooco,ciagĩrĩire Mwathani,\n" +
                "Rĩtwa rĩake nĩrĩega ma,norĩo rĩtũkenagia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Hĩndĩ ĩrĩa agacoka thĩ, na riiri wake mũingĩ\n" +
                "Nĩtũgakena mũno ma,tũgĩtũũrania gwake.   \n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("141. Ukaani Inyui Eehia");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "ũkaani inyuĩ eehia, Mũũke kwĩ Mwathani,\n" +
                "Muone ũhoreri wake, Endeete  kũmũhe.\n" +
                "\n" +
                "\n" +
                "Mwĩtĩkiei, mwĩtĩkiei, mwĩtĩkiei rĩu,\n" +
                "Nĩ Mũkũũri, nĩ Mũkũũri, Mũkũũri wanyu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Atuoneire kĩgoongona, tũninĩrũo meehia,\n" +
                "Akĩruta muoyo wake, Nĩguo tũhonoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Njĩra ĩrĩa ya Mwathani, Nĩ ya ũhoreri,\n" +
                "Tũgere njĩra ĩo rĩu, nĩguo tũhonoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Noguo ithuĩ ithuothe, Nĩtũthiĩ hamwe,\n" +
                "Tũtũũranie kũu igũrũ, Tene o na tene.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("142. Jesu Kristo Niegutwita");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ Kristo nĩegũtwĩta, Tũũmĩrĩrie moogwati,\n" +
                "O mũthenya nĩoigaga, ũkaani mũũhikĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Hingo ciothe cia ũtoonga, kana cia magerio,\n" +
                "Nĩendaga tũmwĩhoke, Tũmwendage makĩria\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Thĩini wa wĩtĩkio witũ, Tũgĩe na gĩkeno,\n" +
                "Tondũ Jesũ e hakuhĩ, Gũtũtwara igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mwathani Jesũ tũteithie, Tũkwĩhokage kũna,\n" +
                "Tũtikahootwo nĩũndũ, Twĩ mooko-inĩ maku.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("143. Wi Munogi Na Muthiini (NZK 112)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Wĩ mũnogu na mũthĩĩni? Nĩũgwĩtwo rĩu;\n" +
                "ũnogoke ũhurũke kwĩ Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Marũũri ma kũmũmenya mahaana atĩa?\n" +
                "Nĩ irema cia magũrũ na mooko.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "O na thũũmbĩ aarĩ nayo ĩĩ Mũtwe wake?\n" +
                "Aarĩ nayo Na nĩ thũũmbĩ ya Mĩigua.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Rĩu ingĩmuuma thuutha, Nĩ kĩĩ ngaheeo?\n" +
                "Nĩngathĩnio, no ngoro-inĩ Gwĩ thaayũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Ndamũhikĩra na kĩyo, Thuutha ngoona kĩ?\n" +
                "Nĩ gĩkeno Gĩa tene o na tene.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("144. Ukaai Inyui Anogu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "ũkaai inyui anogu, nĩngũmũhuurũkia,”\n" +
                "ũhoro ũyũ nĩguo Jesũ atwĩraga;\n" +
                "ũhoro ũyũ nĩguo wa wendo mũnene;\n" +
                "Wa wega o na thaayũ, Gĩkeno kĩnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũkaai inyuĩ orũũri, Ndĩmuonie njĩra,”\n" +
                "Nĩ Mũgaambo wa Jesũ ũgũtwĩra”ũkai”\n" +
                "Ngoro ciitu nĩ nditũ, Na twĩ nduma-inĩ;\n" +
                "Nĩegũtũhe gĩkeno, tondũ nĩ mũigua tha.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "ũkaai inyuĩ ahĩĩnju, Nĩ ngũmũhe hinya;\n" +
                "ũhoro ũyũ mwega, na nĩguo wa muoyo,\n" +
                "Thũ iitũ ĩĩ na hinya, na mbaara nĩ nene,\n" +
                "No mũtoongoria witũ, nĩwe wa gũtooria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "“ũrĩa ũgũũka gwakwa, ndikamũingata,”\n" +
                "ũhoro mwega noguo, wa kũnina guoya\n" +
                "Jesũ nĩatwĩtaga, ithuothe eehia,\n" +
                "Nĩtũthiĩ mĩtũkĩ, tũhonokio nĩwe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("145. Nduuriri Niciinamirire");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndũũrĩrĩ nĩciinamĩrĩre, Gĩtĩ-inĩ kĩa Jehova,\n" +
                "Nĩwe ũheanaga muoyo, Nowe ũheanaga muoyo.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩmakaigua mĩgaambo iitũ, kũu igũrũ twacemania,\n" +
                "Ngiri nyingĩ igakũgooca, ciũnganĩte mbere yaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Uuge waku wĩ kũũndũ guothe, wendo waku ndũgaathira,\n" +
                "ũhoro wa ma nĩũgũtũũra, ũrũmĩte ota rũaro.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("146. Utugi Wa Magegania");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "ũtugi wa magegania ,nĩ wa honokirie\n" +
                "Rĩrĩa ndarĩ mũtumumu ngĩhingũka maitho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Nĩ ũtugi wanyonirie,gwĩtigĩra Ngai\n" +
                "ũkĩnina guoya ngoro,rĩrĩa ndetĩkirie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Mogwati nĩ maingĩ mũno, onamo magerio\n" +
                "No ũtugi ũcio wiki,nĩ ũkanginyia gwaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Mwathani nĩ anjĩrĩire,irathimo nyingĩ\n" +
                "Ndĩhokaga kiugo gĩake muoyo wakwa wothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\n" +
                "Na ũtugi watũkinyia,nĩĩ tũgakenaga\n" +
                "Tũkĩgooca gatũrũme,mĩaka ngiri nyingĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("147. Ni Nguuga Ugoocwo Ngai");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩnguuga ũgoocwo, Ngai wakwa, Rĩu gũgĩtukatuka,\n" +
                "ũgoocwo tondũ mũthenya watinda ũkĩndaathima.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Njoheera tondũ wa Jesũ, meehia ma mũthenya ũyũ,\n" +
                "Nĩgeetha njigue wega rĩu, Itanathiĩ gũkoma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndĩrĩigua guoya ndakoma, Tondũ ngaaroka gũũkĩra:\n" +
                "‘Honokia geetha niĩ ngĩkua, ngaamenya no ngaariũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "‘He toro mwega ũrĩrĩ, O nawe raara, hakuhĩ.\n" +
                "Rũciinĩ ngoona hinya ma, Wa gũkũrutĩra wĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Ingĩremwo nĩ gũkoma, ũ ‘he ũhoro ngene ma:\n" +
                "Irooto njũru kana kĩ, ũgirie I’ nyamaarie.\n" +
                "\n" +
                "6.\t\n" +
                "Goocai Ngai na gũũkũ thĩ! Na mũmũkumie Matu-inĩ!\n" +
                "Mũgooce Ngai witũ mũno, ithe na Mũũriũ na Roho.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("148. Ninjaangite O Kuraya (NZK 118)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩnjangĩte o kũraya,nĩ ngwĩrira rĩu;\n" +
                "Ndigane na mawaganu,nĩngũinũka.\n" +
                "\n" +
                "\n" +
                "Nĩngũka harĩ we,ndige gũcanga\n" +
                "Ndagũthaitha ũnjiyũkie,nĩngũinũka (Ngai).\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Nĩ ndete mĩaka mĩingĩ,nĩngwĩrira rĩu;\n" +
                "Nĩ ngwĩrira itarĩ nganja,nĩngũinũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Nĩ nogete nĩ gwĩthũkia,nĩngwĩrira rĩu;\n" +
                "Nĩ ngwĩtĩkia ndeto ciaku,nĩngũinũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Wee nĩwe mwĩhoko wakwa,nĩngũcooka rĩu;\n" +
                "Nĩ ũndũ nĩwe wanguĩrĩire, Jesũ nĩndooka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\n" +
                "Ndĩmũigane nĩ thakame, Yaku Jesũ rĩu;\n" +
                "Thambia there kũna kũna, Jesũ nĩndooka.  \n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("149. Kwi Na Rui Rua Thakame (NZK 113)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kwĩ na rũũĩ rũa thakame, Ya Imanueli Mwene,\n" +
                "Na eehia marikĩtio ruo, mathiraga gĩko biũ,\n" +
                "Magathira gĩko gĩothe, Magathira gĩko gĩothe,\n" +
                "Na eehia marikĩtio ruo, mathiraga gĩko biũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mũici ũcio nĩakenirio, Nĩ thakame ya Jesũ,\n" +
                "Na niĩ ndagĩũka kũrĩ We, Yahota gũtheria,\n" +
                "Yahota gũtheria guothe, Yahota gũtheria guothe,\n" +
                "Na niĩ ndagĩũka kũrĩ We, Yahota gũtheria,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nginya ndoona rũũĩ rũu, Rũa thakame ya Jesũ,\n" +
                "No ngumagia wendo wake, ndigatiga o na atĩa.\n" +
                "Rũĩmbo rũakwa no rũa Jesũ, Rũĩmbo rũakwa no Jesũ.\n" +
                "Ndũire ngumagia wendo wake, ndigatiga o na atĩa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Rĩrĩa ngariũka ngainaga, Rũĩmbo rũega kwĩ rũrũ,\n" +
                "Jesũ acooka anyambatie, Ngamũinĩra igũrũ,\n" +
                "Ngainĩra Jesũ wega, Ndĩmũinagĩre wega,\n" +
                "Jesũ acooka anyambatie, Ngamũinĩra igũrũ\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("150. Mwathani Jesu Ngwenda Gutherio (NZK 114)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani Jesũ ngwenda gũtherio biũ,\n" +
                "Ngwenda ngoro yakwa ituike yaku,\n" +
                "Waraganie ithũngũri cia meehia,\n" +
                "ũũthaambie riu nduike mũtheru biũ.\n" +
                "\n" +
                "\n" +
                "Mũtheru biũ, nduike mũtheru\n" +
                "Wathaambia ningũtuika mũtheru biũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mwathani Jesũ nduiria o ro riu\n" +
                "Tũma nduike kigoongona ki muoyo,\n" +
                "Ndeheana ngoro, ningi ndi wothe;\n" +
                "ũthambie riu nduike mũtheru biu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Kwoguo  Mwathani ndakũhooya mũno,\n" +
                "Nduritie ndu magũrũ-ini maku,\n" +
                "Ndũingataga andũ mooka kũri Wee,\n" +
                "ũthaambie riu nduike mũtheru biũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("151. Mwathani Nii Nimbataire");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani niĩ nĩ mbataire, ũthambie gĩko gĩthire,\n" +
                "Na mwaki kana na maaĩ ũtherie biũ ũtherie biũ.\n" +
                "ũtherie biũ Mũhonokia, thĩinĩ na nja ngoro yothe,\n" +
                "Ona angĩkorũo no mwaki kinya ngũĩre mehia makwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩ ndĩkenaga mũno ma, wanyonereria maũndũ,\n" +
                "No ũrĩa mbatairo nĩguo no ngoro, yakwa ĩthere.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ngoro yakwa ĩtarĩ theru ndingĩhota gũkũũrana,\n" +
                "Roho waku we Mwathani, Theria rĩu, Thambia rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ngeretie na hinya wothe gũtiga meciria moru,\n" +
                "Na ndingĩhota gũtiga nĩnemetwo nĩ gwĩtheria.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("152. Nimukinyite Hari Jesu (NZK 117)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩmũkinyĩte harĩ Jesũ witũ, Mũtherio biũ nĩ Gatũrũme?\n" +
                "Wega ũcio wake nĩũtũiyũrĩĩte? Twatherio nĩ thakame ĩo?\n" +
                "\n" +
                "\n" +
                "Gũthambio, Gũthambio, Tũthambĩtio nĩ Gatũrũme?\n" +
                "Ngoro ciitũ ithaambagio na thakame, ya Gatũrũme karĩa ka Ngai?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩmũkinyĩte harĩ Mũhonokia, Na mũgatherio nĩ thakame,\n" +
                "Nĩyo iiki ĩngĩtheria ngoro, Thakame ĩyo ya Gatũrũme?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Rĩrĩa Mũhikania agacooka thĩ, Mũgakorũo mũtheretio bĩũ!\n" +
                "Ngoro cianyu nĩigakinya igũrũ, Ithaambĩtio nĩ thakame ĩo?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Inyuĩ tigai meehia manyu, Mũthaambio nĩ thakame yake;\n" +
                "Ndũkuona gĩthima nĩkĩiyũrĩte, Tũthaambagio nĩ gatũrũme?\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("153. Jesu Niaroiga (NZK 119)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩaroiga, “we ndũrĩ na hinya;\n" +
                "Wĩhũũge ũhoe, ũka mwana wakwa.\n" +
                "\n" +
                "\n" +
                "Thiirĩ  wa mehia ,we nĩandĩhĩire\n" +
                "Hau Mũtharaba-inĩ,na akĩnjohere.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Jesũ nĩnyoonete,ũhoti ũcio waku;\n" +
                "Wahota gũtheria,ngoro ĩna gĩko.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Ndĩngĩkĩrandũra ,wega waku mũingĩ;\n" +
                "Rĩu nĩ ngwĩtheria  ,na thakame yaku.\n" +
                "\n" +
                "4\n" +
                ".Rĩrĩa ngarũũgama ,O kũu matu-inĩ\n" +
                "Thũũmbĩ nĩngamĩiga magũrũ-inĩ ma Jesũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("154. Jesu Njikaria Hau Mutharaba-ini");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Jesũ njikaria hau Mũtharaba-inĩ,\n" +
                "He na rũĩ rũa muoyo rũa gũthambia mehia.\n" +
                "\n" +
                "\n" +
                "Mũtharaba waku noguo ndĩkumagia,\n" +
                "O kinya ngahurũka, o kũu matu-inĩ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.  \n" +
                "Hau mũtharaba-inĩ he na tha na wendo,\n" +
                "Na njata ya rũcinĩ ĩnjaragĩra ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Hau mũtharaba-inĩ Gatũrũme ka Ngai\n" +
                "Nĩkangũũrĩire wega ũrĩa anyendete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.  \n" +
                "Hau mũtharaba no ho ngweterera\n" +
                "Ndĩ na kwĩrĩgĩrĩra nginye o matu-inĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("155. Hari Jesu Ngwiheana (NZK 122)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Harĩ Jesũ nwĩheana ,ngoro yakwa o na indo\n" +
                "Nĩngũcĩrĩirio nĩ wendo , nĩkĩo ngwĩneana biũ.\n" +
                "\n" +
                "\n" +
                "Ngwĩneana biũ, ngwĩneana biũ;\n" +
                "Nĩngũcĩrĩrio nĩ wendo ngwĩneana biũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Harĩ Jesũ ngwĩneana ,ndĩmũinamagĩrĩre;\n" +
                "Nĩndigĩte ikeno ciothe Jesũ ũnyamũkĩre!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Harĩ Jesũ ngwĩneana ,ngarũra nduĩke waku;\n" +
                "Roho amenyagĩrĩre matukũ makwa mothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Ndĩheanĩte kwĩ Jesũ na rĩu nĩ nyonete\n" +
                "Gĩkeno kĩa ũhoonokia ngũgooca rĩĩtwa rĩake\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("156. Kwi Na Karima Hakuhi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kwĩ na karĩma hakuhĩ, Na mũciĩ mũnene,\n" +
                "Nĩho Mwathani o tene, Aambirwo mũtĩ-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũguo nĩ ma nĩatuonirie, ũrĩa atwendeete,\n" +
                "Na ithuĩ tũmwendete o ũguo, Na tũmũiguage.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Aathĩĩnirio nĩ ũndũ witũ, Makĩmũnyamaaria,\n" +
                "Makĩmwamba mũtĩ igũrũ, Nĩguo tũhonoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩaitire thakame yake, Atũhootanĩre,\n" +
                "Tũiguithanio na Ngai, Mũhonokia witũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("157. Jesu Nioirire Thakame");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩoirire thakame; nĩaakuire mũtĩ-inĩ,\n" +
                "Nĩ arutire muoyo wake, Atũhonokie.\n" +
                "Tũteithie Jesũ tũkũhe, Ngoro ciitũ rĩu,\n" +
                "Na rĩrĩa ũgacooka thĩ, ũtũtware igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩ tondũ wa wĩhia wĩtũ, Nĩambirwo mũtĩ-inĩ,\n" +
                "Gũtirĩ wendo mũnene, ta wendo wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ ndekwenda thakame, Na nyama cia mbũrĩ,\n" +
                "Nĩekwenda mũhera witũ, Na wendo wa ngoro.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("158. Wi Munogu Na Ugatangika (NZK 124)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Wĩ mũnogu na ũgatangĩka, Thiĩ wĩre Jesũ  mathina maku,\n" +
                "Nĩũrũmbũyagia ikeno ithiraga, Thiĩ wĩre Jesũ wiki.\n" +
                "\n" +
                "\n" +
                "Thiĩ wĩre Jesũ mathĩna maku, Nĩ mũrata waku ki,\n" +
                "Gũtirĩ na mũrata ta Jesũ, Thiĩ wĩre Jesũ wiki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Maithori nĩ maranyũrũrũka, Thiĩ wĩre Jesũ mathĩna maku,\n" +
                "Kana nĩ mehia maragũthĩnia, Thiĩ wĩre Jesũ wiki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Nĩũretigĩra thĩna na kĩeha, Thiĩ wĩre Jesũ mathĩna maku,\n" +
                "Kana nĩũtangĩkagĩra rũciũ, Thiĩ wĩre Jesũ wiki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Wecirĩria gĩkuũ nĩũmakaga, Thiĩ wĩre Jesũ mathĩna maku,\n" +
                "Nĩũcaragĩa ũthamaki wake, Thiĩ wĩre Jesũ wiki.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("159. Nindaakurutiire Muoyo Wakwa (NZK 98)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩndaakũrutĩire, muoyo wakwa mwene,\n" +
                "Nĩguo ũhonokio ũrutwo gĩkuũ-inĩ.\n" +
                "\n" +
                "\n" +
                "Nĩ kĩĩ ngũimĩte wee? Wee ũheete kĩ?\n" +
                "Nĩ kĩĩ ngũimĩte wee? Wee ũheete kĩ?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩndaakũrutĩire, mĩaka yakwa yakwa thĩ ĩno,\n" +
                "Nĩguo warahũke, ũgatũũre igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Niĩ nĩngũreheire, gũũkũ thĩinĩ wa thĩ;\n" +
                "Wendo na ũhonokio, ciumĩte igũrũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("160. Warora Mutharaba Wa Jesu (NZK 125)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Warora mũtharaba wa Jesũ, nĩũkuona muoyo ti itherũ\n" +
                "Riiri wa thĩ o na ũtonga, Ndiracona thengia mũtharaba.\n" +
                "Warora, mũtharaba nĩũkwĩgĩĩra na muoyo\n" +
                "Mũtĩ-inĩ wa Kalwarĩ, Na ndũkũrĩhio kĩndũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndacũũthĩrĩria ngona Jesũ ho, Ngũigua ta ngwenda gũkena,\n" +
                "Kĩbii kĩa magerio gĩoka ndĩonaga hinya mũtharaba-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndĩroraga mũtharaba wake ndĩhokee Ngai wakwa,\n" +
                "Ndangĩreka ngwe nĩ magerio Tondũ nĩndĩrĩmwathĩkagĩra.\n" +
                "\t\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("161. Jesu Ngwihokete (NZK 123)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ ngwĩhokete, ũtuĩke Mũndwari, O we wiki:\n" +
                "Thikĩrĩria rĩu, Na ningĩ ũtherie,\n" +
                "Ndĩ waku waku ki, kuuma rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "He hinya wa ngoro, wa kũndeithĩrĩria ndikanagwe:\n" +
                "Tondũ nĩwakuire, Nĩ getha honokie,\n" +
                "Nĩngwendete mũno, Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Gũkũ nĩndangagwo, nĩ mathĩna maingĩ, O na kĩeha,\n" +
                "Harĩa he na ndũma, nĩũrĩndongoragia,\n" +
                "Na niĩ ngume thutha, Mwathi wakwa.\t\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("162. Thiini Wa Eehia Othe (NZK 121)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Thĩinĩ wa eehia othe, Ndĩ mũtoongoria wao,\n" +
                "Tiga Jesũ okire, Agĩkua nĩ ũndũ wakwa,\n" +
                "Nĩaitire thakame, Nĩguo ngatũũra muoyo.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũyũ nĩ wendo wake, Nĩ wendo mũnene ma,\n" +
                "Wendo ũrĩa mũrikĩrũ, wendo ũtarĩ mũthia,\n" +
                "Wendo ũrĩa wanjaririe, Hĩndĩ ĩrĩa itamwendeete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "O na ndĩ mwĩhia mũno, Kristo nĩanjiganĩte,\n" +
                "Nĩoĩ mathĩĩna makwa, Ndarĩra, akarĩra,\n" +
                "O na mbaara-inĩ yakwa, Nowe ũgitĩri wakwa.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("163. Nimbatairio Niwe (NZK 126)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩmbatario nĩwe , o hingo ciothe;\n" +
                "Nandirĩ na ũngĩ mwagĩrĩru;\n" +
                "\n" +
                "Jesũ nĩngũbataire, mũno hingo ciothe;\n" +
                "Kĩhotithie mũkũũri nĩnjũkĩte.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Nĩmbatairio nĩwe , ũnjikarie;\n" +
                "Magerio no tũhũ matingĩndooria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Nĩmbatairio nĩwe ũndũ-inĩ wothe;\n" +
                "ũtũũro no tũhũ wa kũraihu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Nĩmbatairio nĩwe , ũndungate;\n" +
                "Ciĩranĩro ciaku ndĩcihingie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\n" +
                "Nĩmbatairio nĩwe ,hoti ya mothe;\n" +
                "Ndĩ waku waku ki , matukũ mothe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("164. Riria Nguigua Wega (NZK 127)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Rĩrĩa ngũigua wega, hamwe na thaayũ, o na rĩrĩa ndĩ na thĩĩna;\n" +
                "Maũndũ-inĩ mothe nĩũmenyithĩtie, ndĩ na thaayũ ngoro-inĩ yakwa\n" +
                "\n" +
                "\n" +
                "Nĩ kwega, ngoro-inĩ,\n" +
                "Nĩ kwega ngoro-inĩ yakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "shaitani o na anyarira mũno, nĩngeyũmĩrĩria tondũ,\n" +
                "Kristo nĩoĩ atĩ ndirĩ na hinya, akĩnguĩra nĩguo honoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ngũiga meehia makwa mothe, ti nuthu, ngũmaiga mũtharaba-inĩ;\n" +
                "Ndĩcooka kũgĩa na kĩrumi rĩĩngĩ, ndĩ na thaayũ ngoro-inĩ yakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mwathani hiũha gũcooka thĩ narua, rĩrĩa coro ũkaahuuhwo;\n" +
                "Rĩrĩa ũgooka niĩ ndikaamaka ndĩ na thaayũ ngoro-inĩ yakwa.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("165. Numiriire Jesu (NZK 128)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nũũmĩrĩire Jesũ, O ta ũrĩa oigĩte,\n" +
                "Njĩra ciakwa nĩ amũrĩkĩire;\n" +
                "Na ingĩmũkenia ndangĩndiganĩria;\n" +
                "Nĩngwĩtĩkia na njathĩkage.\n" +
                "\n" +
                "\n" +
                "(Kũmwĩtĩkia) njĩra ya ma nĩ ĩno,\n" +
                "Ya gĩkeno kĩa Jesũ; ĩtĩkia wathĩke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nduma ndirĩ nayo, o na kana kĩbii,\n" +
                "Tondũ Jesũ nĩanjehereirie;\n" +
                "Nĩngũthaambia kĩeha, O na ngaanja ciothe,\n" +
                "Nĩngwĩtĩkia na njathĩkage.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Rĩrĩa ndĩ na thĩĩna, kana kĩeha kĩĩngĩ,\n" +
                "Angũũranagĩria na wendo,\n" +
                "Ruo, kĩeha, cioka, We akandaathima.\n" +
                "Nĩngwĩtĩkia na njathĩkage.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndĩgĩigua gĩkeno, kana njiigue thaayũ,\n" +
                "Itahuunjĩtie Jesũ kũna;\n" +
                "Nĩnjiguagĩrwo tha, na ngeendwo nĩ Jesũ,\n" +
                "Nĩngwĩtĩkia na njathĩkage.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("166. Ningenagio Ni Kwihoka (NZK 129)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩngenaga nĩ kwĩhoka, Mũhonokia makĩria,\n" +
                "Ndamwĩtĩkia nĩ nyonete, kĩhurũko ngoro-inĩ.\n" +
                "\n" +
                "\t\n" +
                "Jesũ nĩngwĩhokete ma, nĩnyonete wĩ njamba;\n" +
                "Jesũ wĩ ma hingo ciothe, ciĩranĩro-inĩ ciaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Nĩngenagio nĩ kwĩhoka, Mũhonokia makĩria,\n" +
                "Gwĩtĩkia thakame yake, nĩthambĩtio wothe biũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " Nĩngenagio nĩ kwĩhoka, Mũhonokia makĩria,\n" +
                "Nĩaheaga hĩndĩ ciothe,  muoyo wake na thayũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.  \n" +
                "Nĩngenagio nĩ kwĩhoka, Mũhonokia makĩria,\n" +
                "Jesũ mwendwa na mũrata, njĩkarĩa hĩndĩ ciothe.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("167. Meehia Maku Mangikoruo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mehia maku mangĩkorũo mahaana gakarakũ,\n" +
                "Nĩũgũtherio mothe mothe ũhaane ta ira.\n" +
                "O na makorũo nĩ matune, O ta thakame.\n" +
                "O na matuĩka matune, O na matuĩka matune,\n" +
                "Mũno ta gakarakũ, ũkahaana ira.\t\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mũgambo ũyũ ũgũgwĩta ũcokerere Ngai, \n" +
                "Nĩ Ngai wa matha maingĩ, O na wendani mũingĩ.\n" +
                "Igua mũgambo ũcio wake, Coka harĩ we.\n" +
                "Igua mũgambo ũcio wake, Igua mũgambo ũcio wake,\n" +
                "Ndũcokerere Ngai, Ndũcokerere Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩũkũrekerũo ũremi waku wa hĩndĩ ndaya,\n" +
                "Na ndũkaũririkana rĩngĩ hingo ya muoyo.\n" +
                "Nĩũkũrekerũo remi waku, wa hĩndĩ ndaya,\n" +
                "Na ndũkaũririkana, Na ndũkaũririkana,\n" +
                "ũcio wa hĩndĩ ndaya, ũcio wa hĩndĩ ndaya.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("168. Ti Itheru Twi Muraata (NZK 130)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ti itherũ twĩ mũrata,ũtahana arĩa angĩĩ\n" +
                "Jesũ nĩrĩo rĩtwa rĩake,nowe mũtũhoeri.\n" +
                "Kaĩ ũhoro ũcio naguo,nĩguo wa magegania\n" +
                "Atĩ tũngĩhoya Ngai,Ndangĩrega kũigua.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Rĩu tũtĩraga nĩkĩĩ,kũmwarĩria kaingĩ ma\n" +
                "Nĩtũrĩkanĩre nake,tondũ nĩguo endaga\t\n" +
                "Ona tũngematha guothe,tuona ũngĩ ta ũyũ\n" +
                "Aca tũtikona ũngĩ, ũngĩtũigua ta Jesũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "O mũthenya tuona ndeto,cia mũtino kana kĩĩ\n" +
                "Tũngemakio nĩ ũrirũ, githĩ to tũmwĩre we\n" +
                "Arata aitũ arĩa ange, rĩmwe matũmenaga \n" +
                "No Jesũ ndangetĩkĩra,gũtũte nginya tene.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("169. Mabataro-ini Ma Ruuciu (NZK 131a)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mabataro-inĩ ma rũũciũ, Ndikũhooya;\n" +
                "No kĩngitĩre ndigeeke, Meehia ũmũthĩ;\n" +
                "Ndikaanarie ciugo njũrũ, Wee Mũkũũri,\n" +
                "Ndigeciirie ũndũ mũũru, ũmũthĩ ũyũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Reke ndute wĩra mwega, Ndakũhooya;\n" +
                "Ndeithia nduĩke mũndũ mwega, Hingo ciothe;\n" +
                "Njĩkage ũrĩa wendeete, Na ngaathĩka;\n" +
                "Ndute mwĩrĩ wakwa ũmũthĩ, Kĩgongoona.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Korũo no ngue ũmũthĩ, O narua,\n" +
                "Ndĩhoke o ciĩranĩro, Ciaku Ngai.\n" +
                "Mabataro-inĩ ma rũũciũ, Ndikũhooya;\n" +
                "Kĩnyiite oro ũmũthĩ, ũndoongorie.\t\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("170. Mwathani Ni Muriithi (NZK 132)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani nĩ mũrĩithi, ndirĩ kĩĩndũ ingĩaga,\n" +
                "Nyeki-inĩ ĩrĩa nduru nĩkuo andĩithagia,\n" +
                "Aaheaga maaĩ me na ũhuurũko.\n" +
                "Na akanjokia hĩndĩ ĩrĩa njũrĩte.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Metha nĩ ũnjarĩire, mbere ya thũ ciakwa,\n" +
                "Na gĩkombe gĩakwa kĩiyũire gĩkoina;\n" +
                "ũhakĩĩte maguta mũtwe wakwa.\n" +
                "Nĩkĩ kĩĩngĩ ingĩũria kuuma kũrĩ we?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "O na ingĩgera mũkuru wa gĩkuũ.\n" +
                "Nĩ mũrori wakwa ndigĩĩtigĩra.\n" +
                "Mũthĩĩngi waku nĩguo ũndiraga.\n" +
                "Rĩrĩa wahoreria no ngaigua wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Wega o na ũtugi itwarane na niĩ.\n" +
                "Matukũ makwa mothe rĩu nginya tene;\n" +
                "Na niĩ ngũtũũra nyũũmba-inĩ ya Ngai.\n" +
                "Thĩini wa ũthamaki ũcio, wa wendo waku Ngai.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("171. Tuura Wi Mutheru (NZK 134)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũũra wĩ mũtheru ohĩndĩ ciothe;\n" +
                "Tũmaga ũrata na arĩa etĩkia;\n" +
                "Waragie na Jesũ ohingo ciothe;\n" +
                "ũhoe irathimo mahinda mothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Tũũra wĩ Mũtheru o hĩndĩ ciothe;\n" +
                "Tũũra mahoya-inĩ o hingo ciothe.\n" +
                "ũngĩcũthĩrĩria Jesũ Mwathani;\n" +
                "Nĩũkũgarũrũka ũhane take.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Tũũra wĩ mũtheru o hĩndĩ ciothe;\n" +
                "Mũtongoria waku atuĩke Jesũ; \n" +
                "Hĩndĩ ya gĩkeno ona kĩeha;\n" +
                "Rũmĩrĩra Jesũ nĩ mũhonokia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Tũũra wĩ mũtheru o hĩndĩ ciothe;\n" +
                "Wathagwo nĩ Roho ngoro-inĩ yaku;\n" +
                "Akuonagĩrĩrie njĩra ya ũthingu;\n" +
                "Nĩagakũhaarĩria wĩra-inĩ wa Ngai.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("172. Igua Wega Ciugo Cia Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Igua wega ciugo cia Jesu, Cia muoyo na ningĩ cia ma,\n" +
                "Andu atheru uririkane, Mendete gwathĩkĩra watho.\n" +
                "\n" +
                "\n" +
                "Gwathĩkĩra watho ucio wake, Gwathĩkĩra watho wa Jesu,\n" +
                "Gukena nĩ arĩa mamuiguaga, Acio nĩo makenaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kuigua gwiki, o na gwĩtĩkia, Gwĩtĩkĩra watho wake,\n" +
                "Itingĩhota gutuhonokia, Thengia o gwathĩkĩra watho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                " mena rutha matonye, muciĩ wao na gĩkeno,\n" +
                "Na matingĩĩhia matirĩ wĩhia, makaheyo mutĩ wa muoyo.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("173. Jesu Mukuuri Niwe Njira");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ Mũkũũri nĩwe njĩra! Ya gũtũtwara igũrũ;\n" +
                "Twarega kũgera njĩra ĩo, Tũgaikio mwaki-inĩ.\n" +
                "\n" +
                "\n" +
                "Ceraga ũtheri-inĩ, ceraga ũtheri-inĩ,\n" +
                "Ceraga ũtheri-inĩ, ceraga ũtheri-inĩ wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Jesũ nĩ njĩra ya ũtheri! Njĩra ĩtarĩ na nduma;\n" +
                "Jesũ nĩwe wathaga ũtheri, ũtheri wa igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ nĩ njĩra ya ũtheri! Nĩtũgere njĩra ĩo;\n" +
                "ũtheri nĩũkũingata nduma, ũtheri wa Kristo.’\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Jesũ nĩ njĩra ya ũtheri! Nĩtũgere njĩra ĩo;\n" +
                "Ngai agatũũra thĩ-inĩ waku, Ngai nĩ ũtheri.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("174. O Guothe Jesu E Ho (NZK 133)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "O guothe Jesũ eho gũtirĩ ũgwati\n" +
                "O guothe andongoretie gũkũ thĩ\n" +
                "O guothe atarĩ ho kũrĩ ũgwati,\n" +
                "O guothe Jesũ eho ndĩngĩtigĩra.\n" +
                "\n" +
                "\n" +
                "O guothe, O guothe, Ndirĩ na guoya.\n" +
                "O guothe Jesũ eho ndingĩtigĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "O guothe Jesũ arĩ ho ndirĩ nyiki,\n" +
                "Andũ nomandige no tiguo Jesũ\n" +
                "O na angĩndwara kũrĩa kũrĩ thĩna,\n" +
                "O harĩa Jesũ arĩ nĩ mũciĩ mwega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " O guothe Jesũ eho o na ingikoma,\n" +
                "Kũria ndigiciirio ni nduma nene,\n" +
                "Ngimenyaga ndigacoka gũcanga,\n" +
                "O guothe Jesũ eho ni mũcii mwega.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("175. Maikarite Thi Utuku");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Maikarĩte thĩ ũtukũ, Arĩithi a mbũri,\n" +
                "Mũrekio wa Ngai nĩokire, Na ũtheri mũnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tigai guoya, “nĩoigire, Maamba kũmaka ma,\n" +
                "Ndĩ na ũhoro mũnene, Naguo nĩ mwega atĩa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩaciarĩtũo ũmũũthĩ, Mũkũũri wa andũ,\n" +
                "Nĩaciarĩtwo  kwa Daudi, Na nĩwe Kristo\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Thiĩi kuo mũkoone, kaana harĩa karĩ\n" +
                "Nĩ kahumbĩtwo na taama, Na kee Mũharatĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Mũrekio aarĩĩkia kuuga, Makĩona Igũrũ,\n" +
                "Arekio a Ngai gĩtutu, mekũina rũrũ.\n" +
                "\n" +
                "6.\t\n" +
                "Igũrũ Ngai nĩagaathwo, Na thĩ gũciaranwo;\n" +
                "Ciana cia Ngai ciendane, Matu-inĩ na thĩ ĩno!\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("176. Wendo Waku We Mukuuri (NZK 37)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Wendo waku Wee Mũkũũri, Nĩũkĩrĩte mothe!\n" +
                "Ikaraga thĩini witũ, Gĩkeno kĩa matu-inĩ.\n" +
                "Jesũ, Wee wĩ wa tha nyingĩ, Mũtheru mũohanĩri,\n" +
                "Kenia ũrĩa wĩ na kĩeha, ũmũgirie maithori.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Iyũria Roho wa wendo, Gĩkundi-inĩ gĩaku;\n" +
                "Reke tũgĩe na gĩkeno, Kĩrĩa gĩa kĩĩranĩro.\n" +
                "Theria ngoro ciitũ rĩu, Mwambĩrĩria mũrĩĩkia,\n" +
                "Tũhe wĩtĩkio mũiganu, Nĩguo tuume ũkombo-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ, ũka kũri ithuĩ, ndũgatũtiganĩrie;\n" +
                "Tũcokerere Mwathani, Ndũgatũtige ithuiki.\n" +
                "Nĩtũgũgũkumia rĩu, O hamwe na Araika,\n" +
                "Inaai na kũmũgooca, toonyai mahooya-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mwathani, rĩĩkia wĩra rĩu, thĩinĩ wa ngoro ciitũ;\n" +
                "Gĩtherie Hekarũ yaku, na ‘honokio mũiganu\n" +
                "No gĩthaambie ciũmbe ciaku, kahinda-inĩ gaka;\n" +
                "Tũhuurũke meehia mothe, Tũtoonye gwaku igũrũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("177. Jesu Ni Muriithi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩ Mũrĩithi wa mbũri ciake,\n" +
                "Rĩu tũrĩ ake tũmakio nĩkĩĩ?\n" +
                "Tũmũhikagĩre agĩtũtwara;\n" +
                "Kũũndũ kwĩ nyoota kana kwega ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Jesũ nĩ Mũrĩithi, Na kayũ gaake,\n" +
                "Nĩko twetereire gatũkenie;\n" +
                "O na gatũkaania no tũĩkendaga;\n" +
                "Tũrĩ rũũru rũake, tũkamũtĩĩa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ nĩ Mũrĩithi, Nake nĩ mwende;\n" +
                "Nĩ gũkua aakuire, Nĩũndũ wa mbũri.\n" +
                "Twĩkĩrĩtwo rũũri tũmenyekage;\n" +
                "Na nĩ rũa thakame ĩo ya Mũrĩithi.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("178. Hindi Njega Ya Mahooya (NZK 135)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Hĩndĩ njega ya mahoya rĩu nĩtwoka harĩ we;\n" +
                "ũtũehererie mathĩĩna,o na mĩnyamaaro yothe\n" +
                "Rĩrĩa gwĩ kĩeha na ruo,nĩũhonagia ngoro ciitũ;\n" +
                "Nĩtũhootaga mathĩna ,hĩndĩ njega ya mahoya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Hĩndĩ njega ya mahoya,tũtware mathĩĩna maitũ\n" +
                "Kũrĩ ũrĩa weranĩirie gũtũteithia arĩa mabataire;\n" +
                "Atwĩraga tũthiĩ gwake na twĩhoke ciugo ciake;\n" +
                "Kwoguo tũmwĩrekererie hĩndĩ njega ya mahoya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Hĩndĩ njega ya mahoya,nĩtũrĩkĩragĩrĩria;\n" +
                "Kwĩhitha kiugo-inĩ kĩa Ngai nginya tũnine rũgendo\n" +
                "Jesũ nĩarĩtũiguaga twamũcaria hingo ciothe;\n" +
                "Nĩtũrĩonanaga nake hĩndĩ njega ya mahoya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Hĩndĩ njega ya mahoya,nĩnyonaga ũhoreri\n" +
                "Ndĩ kĩrĩma-inĩ igũrũ nyonaga mũciĩ wa Ngai\n" +
                "Ngakena atĩa ndakinya kuo heo mwĩrĩ ũngĩ na thũmbĩ;\n" +
                "Na ngakena hingo ciothe ngĩgocaga mũthamaki.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("179. Ihinda Ria Kuhooya (NZK 137)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Ihinda rĩa kũhoya Nĩrĩo ngoro ciitũ\n" +
                "Ciũnganaga he Jesũ Mũrata witũ\n" +
                "Ona nĩtwĩtĩkĩtie ũgitĩri wake\n" +
                "Ngoro yothe hehenje no ĩkahonio wega.\n" +
                "\n" +
                "\n" +
                "Tũkĩmũhoya, Tũkĩmũhoya,\n" +
                "Ngoro yothe hehenje, no ĩkahonio wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ihinda rĩa kũhoya, Nĩrĩo Mũhonokia\n" +
                "Aceeragĩra ciana ciake ciamwita\n" +
                "Nake agaciarĩria, agacihuurũkia,\n" +
                "Ngoro yothe hehenje no ĩkahonio wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Ihinda rĩa kũhoya, Arĩa mageretio\n" +
                "Nĩrĩo Manengagĩra Jesũ mathĩna\n" +
                "Nake  ena tha nyingĩ akamehereria\n" +
                "Ngoro yothe hehenje no ĩkahonio wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                " Ihinda rĩa kũhoya, Nĩrĩo tũmenyaga\n" +
                "ũrĩa wothe twahoya We nĩekũhingia\n" +
                "Namo mathĩna maitũ agakuua mothe,\n" +
                "Ngoro yothe hehenje no ĩkahonio wega.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("180. Nindiikurumagirira (NZK 138)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩndĩĩkũrũmagĩrĩra, kũrĩa guothe ũngĩenda,\n" +
                "Nĩ wega ũndoongoragie, na niĩ ngũrũmĩrĩre.\n" +
                "\n" +
                "\n" +
                "Nĩngũkũrũmĩrĩra Wee, tondũ nĩwangũĩrĩire,\n" +
                "Tondũ wa tha ciaku Jesu, ngwenda ngũrũmĩrĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "O na njĩra ĩngĩthũũka, ĩgĩe mĩigua na ngũĩ,\n" +
                "Wee nokuo wagereire, na niĩ no nũmĩrĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ingĩcemania na thĩĩna, ona magerio guothe,\n" +
                "Ndamenya ũrĩa waathĩnire, no ngakũrũmĩrĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "O na ningĩ ũngĩndwara, magerio-inĩ mooru ma,\n" +
                "Wee nokuo wagereire, na niĩ no nũmĩrĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "O na ningĩ ũngĩndwara, nduma-inĩ ĩrĩa ndiku,\n" +
                "Wee nĩwamĩtiririe, Na niĩ no nũmĩrĩre.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("181. Nyinirai Oro Riingi (NZK 139)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nyinĩrai oro rĩĩngĩ, Ciugo cia Ngai cia muoyo;\n" +
                "Wega wake ndĩwonage, ciugo icio cia muoyo,\n" +
                "Ciugo ici njega, Ii na ũhoro wa ma.\n" +
                "\n" +
                "\n" +
                "Ciugo njega ii na muoyo, ciugo njega cia Ngai,\n" +
                "Ciugo njega ii na muoyo, ciugo njega cia Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kristo nĩatũheete ithuothe, Ciugo cia Ngai cia muoyo;\n" +
                "ũrĩa wĩ na meehia nĩaigue, ciugo icio cia muoyo;\n" +
                "Iheanagwo tũhũ, nĩguo itũhonokie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ciugo njega cia Injili, Ciugo cia Ngai cia muoyo;\n" +
                "Nĩirehagĩre andũ thaayũ, Ciugo icio cia muoyo,\n" +
                "Nĩ icio itũtheragia, Na ũthingu wa Kristo.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("182. Njoya Uguo Ndarii Jesu (NZK 140)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Njoya ũguo ndarĩi Jesũ Nĩwanjitĩire thakame.\n" +
                "Na tondũ nĩũnjĩtĩte Jesũ njoya njoya, rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ti wĩra wakwa gwĩtheria Nĩwe ũgũtheria kũna,\n" +
                "Nĩwe waitire thakame Jesũ njoya njoya rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Ndirĩ ndona gĩkeno thĩ No guoya na mbaara nene\n" +
                "We Gatũrũme ka Ngai, Jesũ njoya njoya rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "ũtumumu wakwa wothe, ũkĩa wakwa na gwĩtanga\n" +
                "Nĩũkũnjehereria mothe Jesũ njoya njoya rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\n" +
                "Tondũ wa ciĩrĩro ciaku Theria rĩu ũndekere,\n" +
                "Nyamũkĩra nduĩke waku Jesũ njoya, njoya rĩu.\n" +
                "\n" +
                "6. \n" +
                "Wendo waku noguo wiki ũtooretie ngoro yakwa,\n" +
                "Rĩu ngũtuĩka waku ki Jesũ njoya njoya, njoya.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("183. Ninguigua Mugambo (NZK 142)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩngũigua mũgaambo, wa Jesũ Mwathani,\n" +
                "Thiĩ ngathaambio na thakame; ya mũtharaba-inĩ.\n" +
                "\n" +
                "\n" +
                "Nĩngũũka Jesũ, Mũtharaba-inĩ,\n" +
                "ũtherie na thakame, ĩo wanjitĩire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngũũka ndĩ o mũmocu, Hinya waku ũ ‘he;\n" +
                "ũthambie gĩko gĩthire, na thakame yaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ nĩandutĩĩte, Wendani, wĩtĩkio;\n" +
                "Rũmia thaayũ ũiguithanio, Gũũkũ thĩ na Igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nyoneete ũhonokio, wega o na thaayũ;\n" +
                "Waku Jesũ ndĩ na hinya, Na ũthingu ũcio waku.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("184. Ndi Waku Jesu (NZK 144)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndĩ waku Jesũ, Mũgaambo waku, Nĩũnyonetie wendo,\n" +
                "Nĩngwenda mũno, atĩ ngwĩtĩkie, Na ũkĩngucie harĩ wee.\n" +
                "\n" +
                "\n" +
                "Jesũ, nguucia (nguucia) njũke harĩ We;\n" +
                "Nginye mũtĩ-inĩ waku.\n" +
                "Jesũ nguucia, nguucia, njũke harĩ We;\n" +
                "Thakame-inĩ ya goro.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũnyamũrage ndutage wĩra, Wa gũgũtungatagĩra,\n" +
                "Njĩkage o ta ũrĩa wendaga, na ndũũre ngwĩhokete.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩngenaga hĩndĩ ya kũhooya, Rĩrĩa ndurĩtie maru,\n" +
                "Hĩndĩ ĩo nĩyo njaragia nawe, Ngaigua mũgaambo waku.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("185. Thi Ino Ni Inogete (NZK 145)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Thĩ ĩno nĩĩnogetie, ndĩrenda o Mũhonokia,\n" +
                "Thĩ ĩno nĩĩhenagia, Jesũ nĩ mwĩhokeku.\n" +
                "\n" +
                "\n" +
                "Tha ciake cia magegania, Wendo ũtangĩthimĩka,\n" +
                "ũhonokio ũcio mũhingu, Nĩ kĩheeo kĩa muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Thĩ ĩno nĩĩnogetie, ndĩrenda Mũhonokia,\n" +
                "Kũrĩ na makũũmbĩ maingĩ, he na rũĩmbo rwega ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Thĩ ĩno nĩĩnogetie, ndĩrenda Mũhonokia,\n" +
                "Rũgeendo rũũrũ rũa thĩ ĩno, gĩkeno nĩkũrĩ We.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Thĩ ĩno nĩĩnogetie, ndĩrenda Mũhonokia,\n" +
                "Ndĩĩhokete mũtharaba, harĩa nyonaga Jesũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("186. Njoya Njoya Mwathani");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Njoya, njoya Mwathani, nduĩke waku, waku ki,\n" +
                "Moko maya ngũkũhe, ũmaathe nginya tene\n" +
                "ũtherie thĩĩnĩ wa thakame, Theria rũĩ-inĩ rũa thakame,\n" +
                "Njoya Mwathani, na indo ciakwa ciothe Ndũĩke waku waku ki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Oya magũrũ makwa, kũria wandũma ngathiĩ,\n" +
                "Oya mũgambo wakwa, ngocage rĩĩtwa rĩaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Kanua gakwa ngũkũhe, njaragie ndeto ciaku,\n" +
                "Mbeca ciakwa o na indo ngũkũhe ciothe ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mĩaka yakwa ya muoyo, hinya na ũũgĩ wakwa,\n" +
                "Nĩngwenda wamũkĩra, ndũũre wĩra-inĩ waku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Nĩndĩkwendaga Jesũ, Njoya njoya o rĩu,\n" +
                "Ndũkandege Mwathani, Ndĩ waku kũna kũna.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("187. Thakame Ya Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Thakame  ya Jesũ, Nĩĩtheretie meehia biũ;\n" +
                "No nĩguo there biũ, No nginya ndike kuo biũ.\n" +
                "\n" +
                "\n" +
                "Ndike biũ, rũũĩ-inĩ, Rũa thakame theru biũ,\n" +
                "Ndike thakame-inĩ Thakame ya goro ki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "O mũthenya, O thaa Nyonaga iraathimo;\n" +
                "Ngũtũũra hooyaga, Nyongererũo hinya ki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ngũtũũra na Kristo, Aandoongoragie wega;\n" +
                "Na mabata makwa, Ndahooya akamahingia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ngoro-inĩ gwĩ thaayũ, Ndĩ thĩ ĩno ya meehia;\n" +
                "No ndiaga kũhooya, Kinya there thĩinĩ biũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("188. Nguucia Hakuhi Nawe Baba (NZK 148)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nguucia hakuhĩ nawe, Baba, O na ũhĩĩmbĩrie,\n" +
                "ũũnjige oro gĩthuri-inĩ, Ngwenda kũhuurũka.\n" +
                "\n" +
                "\n" +
                "ũũngucie hakuhĩ (ũnguucie, hakuhĩ nawe)\n" +
                "Na mĩkaanda ya wendo, (O na mĩkaanda, ya wendo)\n" +
                "ũũngucie (No ũnguucie hakuhĩ, na mĩkaanda ya wendo)\n" +
                "Hakuhĩ nawe (Nguucia hakuhĩ nawe)\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũũngucie Muhonokia wakwa, Tũtikarekanie;\n" +
                "ũũnjigĩrĩre mooko maku, Ndĩmone ũmũũthi.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nguucia na Roho ũcio waku, hanene o nawe,\n" +
                "ũũthambie ũnjĩkĩre hinya, there, na njohorwo.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("189. Haha Mutharaba-ini (NZK 141)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Haha mũtharaba-inĩ, Ngwenda kũrũũgama;\n" +
                "Nĩho kĩĩruru kĩega, Gĩa kwĩyũa riũa;\n" +
                "Ngũmenya no ngoro yakwa, Jesũ akandwara,\n" +
                "Kũrĩa kwega ngahuurũka, Ndige thĩĩna.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Haha nĩ heega mũno, Na he na mwĩhoko;\n" +
                "Haha nĩhonekeete, Wendani mũnene;\n" +
                "Ta ũrĩa Jakubu o tene, Onirio nĩ Ngai.\n" +
                "Mũtharaba nĩguo Njĩra, ya igũrũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ mũtharaba-inĩ Nimaamũrumire,\n" +
                "Nĩ aakuire ahonokie niĩ Nĩndoorĩte:\n" +
                "Na niĩ nĩngũmaka ma, Nĩ maũndũ meerĩ,\n" +
                "Atĩ Jesũ anyeendaga, Ndĩ o mũremi.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mũndũ ũkwenda gũkinya, Kwa Jesũ Igũrũ,\n" +
                "Njĩra ya mũtharaba , Noyo ya matu-inĩ;\n" +
                "Jesũ nĩ Mũhonokia, Hatirĩ na ũngĩ,\n" +
                "Gwĩ gĩkeno kĩnene ma, Kwa Mwathani.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("190. Mwathani Riu Ndigiite Maundu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani rĩu ndigĩte Maũndũ ma thĩ ĩno;\n" +
                "Ngwenda gũkũrũmĩrĩra, nginye mũtĩ-inĩ waku.\n" +
                "Ndĩ na thĩĩna mũingĩ mũno, Andũ othe mangĩndiga;\n" +
                "We, Jesũ, Ndũngĩtĩkĩra; Kũũndiga Kinya tene.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Jesũ, nĩngũririkana, ũrĩa wathũrirũo ma,\n" +
                "Makĩrega gũkwĩhoka, Magĩgũtiga othe,\n" +
                "Ingĩhenio nĩ araata, kana mangĩmena mũno.\n" +
                "Ndĩinaga na ngoro yakwa, Mwathani nĩ mũraata.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Njikaraga ta mũgeendi, ũrarĩrĩire kwene;\n" +
                "Njetereire ngoona mwago, Wa Mũhonokia wakwa,\n" +
                "Ndingĩcũũthĩrĩria thĩĩna, No ũkeengi wa Mwathani,\n" +
                "Ndĩĩtũũraga! Na gĩkeno, Hingo ya Muoyo wakwa.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("191. Ningwenda Nduike Mwana Waku");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩngwenda nduĩke Mwana waku, ũndute, ũnyonie,\n" +
                "Njĩkage o ũrĩa wendaga, Ndeithia ndeithia rĩu.\n" +
                "\n" +
                "\n" +
                "Ndĩ waku …. (kĩũmbe), Ndĩ waku ….(kĩũmbe)\n" +
                "Ndĩ waku Mwathi Jesũ\n" +
                "Ndĩ waku …. (kĩũmbe), Ndĩ waku ….(kiũmbe)\n" +
                "Ndĩ waku waku kĩũmbe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ĩkeno ciathĩ na ũtonga, ũngĩaga ho Jesũ,\n" +
                "Ngũtiga ciothe ngĩgũcaria, Nĩwe mũtongu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndahutatĩra ikeno cia thĩ, Nguhĩrĩria Jesũ,\n" +
                "Thĩini waku wĩ na indociothe, Na kĩhuurũko.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("192. Thamaka Thiini Wakwa (NZK 147)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Thamaka thĩĩnĩ wakwa, Mũthamaki Jesũ,\n" +
                "Ciũria ciakwa o ciothe, ũtuĩke Mũcookia,\n" +
                "ũtũũre thĩĩnĩ wakwa, tuĩka mũndoongoria,\n" +
                "Nduĩke nduungata yaku, Wee wĩ Mũthamaki\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Hekarũ nĩndutĩte, na nĩũmĩtheretie;\n" +
                "Reke riiri ũrĩa waku, ũmũrĩke thĩinĩ,\n" +
                "Thĩ nayo ĩkire ki, Mwĩrĩ naguo ũtuĩke,\n" +
                "Ngoombo njega njathĩki,ũgwathĩkagĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ciĩga ciote cia mwĩrĩ, Nĩigwathĩkagĩre,\n" +
                "Ciikare ciĩharĩirie, Gwĩka o ũrĩa cieruo;\n" +
                "Itarĩ na mateta, kana inegene,\n" +
                "Kana kũnyarirĩka, kana kwĩmakĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Njikare na ũhooreri, itegũtheethũka,\n" +
                "Njikare ndĩharĩirie, gwĩka kwenda gwaku,\n" +
                "Thamaka thiĩnĩ wakwa, Mũthamaki Jesũ,\n" +
                "Ciũria ciakwa o ciothe, ũtuĩke mũcookia.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("193. Kwenda Gwaku Ngai");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kwenda gwaku Ngai, nĩgwĩkĩke, Mũũmbi wa rĩũmba nyũũmba wega,\n" +
                "ũnyũũmbe nduĩke, O ũrĩa ũkwenda, Nĩngwetereire ũthondeke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kwenda gwaku Ngai, nĩgwĩkĩke, ũthuuthuurie mehia mothe biũ,\n" +
                "Na ũtherie mwĩrĩ na ngoro, Ndainamĩrĩra mbere yaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Kwenda gwaku Ngai, nĩgwĩkĩke, Ndirĩ na hinya, ndakũhoya,\n" +
                "Wee wĩ na hinya, O na ũũgĩ, Nĩndooka, ũũhonie Mwathani.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Kwenda gwaku Ngai, nĩgwĩkĩke, Nĩngũruta indo ciakwa ciothe,\n" +
                "Muoyo, mwĩrĩ, O na ũtoonga, Ciothe nĩ ciaku kũna kũna.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Kwenda gwaku Ngai, nĩgwĩkĩke, Wendi wakwa ũũhehenje,\n" +
                "Nĩngwenda ũtuĩke wĩ Mwathani, O na wa indo ciakwa ciothe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("194. Nguuka Mutharaba-ini (NZK 115)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Ngũũka mũtharaba-inĩ, ndĩ kĩonje ndĩ mũthĩni,\n" +
                "Ndige maũndũ mothe, njũke ũnyamũkĩre.\n" +
                "\n" +
                "\n" +
                "Ngũkwĩhoka we wiki, we wiki mwana wa Ngai,\n" +
                "Na ngũinamĩrĩre we ũhonokie Jesũ rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Ngũgũkaira Mũno, tondũ wa ũũru wakwa,\n" +
                "Jesũ nake ekuga, “Ngũgũthambia wothe rĩu.”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Rĩu ngũkũhe ciothe, matukũ makwa na indo,\n" +
                "Ngoro hamwe na mwĩrĩ, nĩ ciaku nginya tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Thakame ĩo yaku, ĩngarũrĩte ngoro,\n" +
                "Ngatiga meerirĩria, nũmagĩrĩre Jesũ\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("195. Baba Ndiri Na Uteithio Ungi (NZK 143)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Baba ndirĩ na ũteithio, ũngĩ tiga waku;\n" +
                "ũngĩkĩeherio harĩ niĩ, ingĩĩka atĩa, Baba?\n" +
                "\n" +
                "Rĩu nĩnjĩtĩkĩtie biũ, Jesũ nĩaakuire,\n" +
                "Nĩaitire thakame yake, nĩguo ahonokie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩnjĩtĩkĩtie ndĩ mwana, waku ũhe hinya,\n" +
                "Hiingĩria mabata makwa, kahiinda-inĩ gaaka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩ gĩkeno kĩigana atĩa, kuona ũthiũ waku!\n" +
                "Menyage mũgaambo waku, ngĩage na wega.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("196. Ciugo Njuru Wee Ungaanie (NZK 150)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ciugo njũru wee ũngaanie, Itikoime kanua-inĩ,\n" +
                "Ngoro njega rigĩrĩria, Nĩmĩ itigaciarie.\n" +
                "\n" +
                "\n" +
                "“Na inyuĩ endanai” Jesũ ekuuga,\n" +
                "(endanai) (endanai)\n" +
                "O ta ũrĩa aatwendire o tene:\n" +
                "“Na inyuĩ endanai”, Jesũ ekuuga,\n" +
                "(endanai) (endanai)\n" +
                "Ciana, iguai waatho ũyũ. (kaba waatho ũyũ).\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Wendo naguo nĩ mũtheru: ũraata nĩ mwega ma,\n" +
                "Ciikarage itarĩ thũũkie, Na ciugo itarĩ njega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩtũtige maraakara, Nĩmaciaraga kĩeha,\n" +
                "Wendo waku Mũhonokia, No ũtũhootanĩre.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("197. Niandoongoragia Mwathani (NZK 151)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩandoongoragia Mwathani, na niĩ nĩngenaga mũno,\n" +
                "Harĩa hoothe ndĩthiaga, nĩ Jesũ ũrĩndoongoragia.\n" +
                "\n" +
                "\t\n" +
                "Andoongoria anyitĩte; na guoko gwake We mwene;\n" +
                "Nĩngũgĩtwarana nake, Kristo We ũndoongoragia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na rĩmwe ndĩ magerio-inĩ, na rĩĩngĩ ndĩ gĩkeno-inĩ;\n" +
                "Marĩa mothe mangoraga, Mwathani Nĩandoongoragia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Guoko gwake kũnyitĩte, ndigateta o rĩ o rĩ;\n" +
                "Kĩrĩa gĩothe arĩheaga, no ndĩrĩkĩamũkagĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ingĩniina wĩra wa thĩ, niĩ ndikoorĩra gĩkuũ;\n" +
                "Nĩũndũ kũhootana kwĩ ho, ndoongoretio nĩ Ithe witũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("198. Hakuhi Nawe Ngai (NZK 152)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Hakuhĩ nawe Ngai niguo ngwenda , hakuhĩ makĩria Ngai wakwa;\n" +
                "Hingo yakwa yothe njikarage nawe ,hakuhĩ makĩria Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Niĩ ndĩ mũgendi rũgendo-inĩ ,ndirĩ ndahurũka ngoro-inĩ\n" +
                "No rwĩmbo nyinaga ũguo ngĩthiaga , hakuhĩ makĩria Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Maũndũ matheru mangucagia , nĩmo maku mega ma igũrũ;\n" +
                "Hĩndĩ ĩrĩa ndĩ toro ũnjikaragie, hakuhĩ makĩria Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Rĩu ngũkũgooca na hinya ma wee ndũũmba ya ihiga rĩa ũhonokio\n" +
                "Rĩrĩa ndĩna thĩĩna ndĩũragĩra gwaku , Hakuhĩ makĩria Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\n" +
                "Rũgendo rwathira rwa gũkũ thĩ ,ũkandwara igũrũ kũu gwaku\n" +
                "Nĩkuo ngakenera tondũ ngatũũraga,hakuhĩ makĩria Ngai wakwa.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("199. Riciiria No Rimwe (NZK 153)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Rĩciirĩa no rĩmwe,\n" +
                "Ndĩ narĩo ngoro-inĩ,\n" +
                "Nĩnguhĩrĩirie igũrũ\n" +
                "Makĩria ya ira.\n" +
                "\t\n" +
                "Gũkuhĩ na gwitũ igũrũ,\n" +
                "Gũkuhĩ na gwitũ rĩu,\n" +
                "Nguonage hakuhĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kuhĩrĩria gwitũ,\n" +
                "Kuo kwĩna ciikaro,\n" +
                "Gĩtĩ-inĩ kĩa Ngai Mũũmbi,\n" +
                "Hakuhĩ na rũũĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ nyongerera,\n" +
                "Wĩtĩkio wakwa;\n" +
                "Na hĩndĩ ĩrĩa ngakinya kũu,\n" +
                "Nguonage hakuhĩ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("200. Njagiriiruo Ngoruo Nake (NZK 154)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Njagĩrĩirũo ngorũo nake, Mũhonokia wakwa,\n" +
                "Rĩrĩa e hakuhĩ na niĩ, ngĩaga na hinya ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Njagĩrĩrirũo ngorũo nake, rĩrĩa ndĩna wĩtĩkio;\n" +
                "Kĩhuhũkanio kĩngĩũka, o na ndihu cia iria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Njagĩrĩirũo ngorũo nake, matukũ makwa mothe\n" +
                "O na kũngĩũka, magerio kana thĩna o wothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Njagĩrĩirũo ngorũo nake, njĩra-inĩ ciakwa ciothe;\n" +
                "Maitho make manyonagia, ikĩro ciakwa ciothe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("201. Kuria Daudi Aaciariiruo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kurĩa Daudi aaciarĩiruo, Tene kwarĩ  kĩugu;\n" +
                "Nĩho rĩmwe haraarire,\n" +
                "Mutumia na Kaana, nyina nĩwe Mariamu,\n" +
                "Mwaana nake nĩwe Jesu.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Jesu nĩoimire Iguru, Akĩĩnyihia guuku thĩ,\n" +
                "Nĩ kiugu-inĩ aaciarĩiruo, Na agĩkomio muharatĩ;\n" +
                "Mwaathani nĩaatuurire, O ta ngĩa na e munene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Na hiingo yake ya waana, Kuhoorera no Jesu;\n" +
                "Nĩendaga Nyina muno; Aarĩ Mwaana muigua ma,\n" +
                "Twaana tutu, mutuĩke, Twaana twega o take.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Aakurire o ta ithuĩ, Na nĩonaga o urĩa tuonaga;\n" +
                "Aaiguaga kĩeha na gĩkeno, Na agatheka na akarĩra;\n" +
                "O na rĩu amenyaga, Twathĩĩna na twakena.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Nĩtukaamuona na maitho, Tondu nĩwe watukuurire:\n" +
                "Mwaana ucio muhooreri, Rĩu nĩ Mwene-Hinya-Wothe;\n" +
                "Ningĩ kuu aathire, Nĩagatwara ciana ciake.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("202. Ngucaria Jesu O Riu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngũcaria Jesũ O rĩu, Tũtindanagie nake,\n" +
                "Nũmagĩrĩre makinya, Make njĩra-inĩ njeke.\n" +
                "Nĩanyendete, nĩanyendete, Nĩnjũi Nĩanyendete,\n" +
                "Nĩanyendete, Akĩnguĩra, Na nĩkĩo Ndĩmwendete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndĩthiaga kũrĩa enda, ũnyinyi ndũngĩngiria\n" +
                "Njĩra yothe ekũgera, ũnyinyi ndũndigithia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Arũngĩi o mũromo-inĩ Ndonye nyume mehia-inĩ.\n" +
                "Ningaringe ndonye thĩinĩ anjigua anjiyũkie.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("203. Mooko-ini Ma Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mooko-inĩ ma Jesũ, hakuhĩ nawe We,\n" +
                "Nĩ ho ngwenda gũikaraga, ngoro ĩhuurũkage.\n" +
                "Araika igũrũ, matitigaga kũina,\n" +
                "Tondũ nĩmaikaraga, hamwe na Mwathani.\n" +
                "\n" +
                "\n" +
                "Mooko-inĩ ma Jesũ, hakuhĩ nake we,\n" +
                "Nĩho ngwenda gũikaraga, Ngoro ĩhuurũkage.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mooko-inĩ ma Jesũ, gũtirĩ mogwati,\n" +
                "Ma kũndigithania nake, Kana gũthĩĩnagia,\n" +
                "Gũkaya, na kũrĩra, kana gũthaanganagia,\n" +
                "Gũtirĩ ingĩĩtigĩra, ndĩ hamwe na Jesũ.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũikare mooko-inĩ, ma Mwathani Jesũ,\n" +
                "Nĩguo atũhe hinya, tũtooragie Shaitani,\n" +
                "Mũthenya na ũtukũ, tũtũũre hamwe nake,\n" +
                "O nginya tũkamuona, kwa Ngai igũrũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("204. Kuria Guothe Angienda Thii");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kũrĩa guothe angĩenda thiĩ, ndikarega o rĩ o rĩ,\n" +
                "Tondũ nĩngũririkana, we nĩanguĩrĩire kalwarĩ.\n" +
                "Jesũ nĩarĩndongoragia, ũtukũ o na mũthenya,\n" +
                "Nĩ mwendwa wakwa wa ngoro, Ndaririkana Kalwarĩ.\n" +
                "\t\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩnyendete watho wake, Anyitĩte guoko gwakwa,\n" +
                "Nĩngenagio nĩ thakame iyo yaitirũo Kalwarĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nganja o na guoya ndirĩ, Mũhoonokia e hakuhĩ,\n" +
                "Njĩrĩgĩrĩire ngamuona, Nĩ mũrata wa Kalwarĩ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("205. Muhonokia E Hakuhi (NZK 157)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũhonokia e hakuhĩ, Na nĩwe watũkuĩrĩire,\n" +
                "Arĩa maandĩkĩtwo igũrũ, Nĩo akongania.\n" +
                "Hakuhĩ, Hakuhĩ, E hakuhĩ o mũromo-inĩ,\n" +
                "Nĩegũũka, Nĩegũũka, Arũngiĩ mũromo-inĩ.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Marũri no maratuonia, Atĩ rĩũ e hakuhĩ,\n" +
                "Nao andũ ake aamũre, Nĩmamwetereire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Thĩ ĩno mbaara ndĩgathira, Gĩkeno thayũ gũtirĩ,\n" +
                "O kinya Jesũ acoke, kũnina waganu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Thĩ ĩno yarĩkia gũthambio, tũgacoka gũtũũra kuo,\n" +
                "Tũtũũre mĩndi na mĩndi, Tũtigakua rĩngĩ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("206. Hatigairie aigana Atia (NZK 158)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Hatigairie haigana atĩa? kinya mũciĩ, ngĩũria ngĩĩrwo\n" +
                "ũtukũ nĩũrahĩtũka na gũcoke gũkĩe.\n" +
                "Kĩgirĩko rũmia njĩra njata nĩĩgũgutongoria;\n" +
                "Kinya ithirĩro rĩkinye tũthiĩ gwa ithe witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Ngĩũria thĩ, ĩrĩa na riũa igĩcokia mũgambo ũmwe\n" +
                "Rĩu ihinda nĩ rĩthiru ,mũthenya nĩ mũũku;\n" +
                "Kĩgirĩko no wĩhũge, marũũri no maratwonia\n" +
                "Thĩ yothe rĩu yetereire cooro wake wiki;\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Ngĩũria njaamba ĩrĩ werũ-inĩ , rũĩmbo rũgĩkĩmĩguucia \n" +
                "ũmĩrĩria ũhotane mbara ti ndaaya rĩu;\n" +
                "Kĩgirĩko ũmĩrĩria ũguucanie ũhootane ;\n" +
                "Kĩheo kĩrĩ ho kũna , Ombara ya thira.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Rĩu mũciĩ ti kũraihu , mũgendi gĩkũngũĩre;\n" +
                "Kĩhonia rĩu nĩkĩo gĩkĩ kĩniarie maithori;  \n" +
                "Kĩgirĩko tũcemanie, kũrĩa gũtagirĩkagwo;\t\n" +
                "Tiga gĩkeno gĩtheri , mũciĩ gwa Ithe witũ\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("207. Ni Uhoro Mwega Ma (159)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩ ũhoro mwega ma rĩrĩa tũkũũigua ithuĩ agendi gũkũ thĩ;\n" +
                "Mĩtũkĩ Mũthamaki nĩegũka rĩngĩ,naguo ũthamaki ũke.\n" +
                "\n" +
                "\n" +
                "Nĩegũka, oke mĩtũkĩ mũno ma,nĩegũka gũkũ thĩ ĩno;\n" +
                "Aiyĩre andũ ake matwaro mũciĩ rĩrĩa agoka Mũthamaki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Mbĩrĩra iria arĩa atheru makomete,ikahingũrwo mariũkio;\n" +
                "Andũ ngiri nyingĩ arĩa makomeete nĩ magacokio muoyo rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Tũtikamũkana rĩngĩ tũrĩ nao,nĩtũkaina nyĩnbo njega;\n" +
                "Magacemanagia rũũrĩrĩ o rũũrĩrĩ , magũrũ-inĩ ma Gatũrũme.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Haleluyah! Amen Haleluyah o rĩngĩ , wendo wake nĩ mũnene;\n" +
                "Tũkamũgocaga tene ona tene , tũgegeete nĩgũtũkũũra.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("208. Aria Atheru Wihuugei (NZK 160)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Arĩa atheru wĩhũũgeni, Hinya wa igũrũ ũkĩenyenyio,\n" +
                "Akiai matawa manyu, Mwathani wanyu nĩegũka.\n" +
                "Nĩegũũka, Jesũ wĩtũ, Oke na ũkengi wake,\n" +
                "Ningĩ oke gũthamaka, Nĩegũũka, Jesũ witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ciĩranĩro ciake ciothe, Nĩ wohanĩri wa mehia\n" +
                "Na matonyo megũkenga, Hamwe na thũmbĩ hunjiai.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mothamaki nĩ maragwa, naco ngaari ciake ciũke,\n" +
                "Hunjiai tha ciake nyingĩ, Coro wake nĩũkũgamba.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndũũrĩrĩ nĩiranangĩka, Kristo no ateng’erete\n" +
                "Kahinda nĩ ta gathiru, ũthamaki nĩ mũkinyu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Ehia rĩu teng’erai, Kristo amũthaithanĩrĩre,\n" +
                "ĩno nĩyo hingo ya tha, Thutha mabuku mahingwo.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("209. Huuhai Coro Mugaambie Muno (NZK 161)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Huuhai coro mũgambie mũno, Jesũ nĩegũũka rĩngi,\n" +
                "Kũngũĩriai na mũine mũno, Jesũ nĩegũũka rĩngi.\n" +
                "Nĩegũcoka, nĩegũũka,\t\n" +
                "Jesũ nĩegũka rĩĩngi\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Gambia irĩma-inĩ ona werũ-inĩ, Jesũ nĩegũũka rĩngĩ,\n" +
                "Ena ũkengi, Mũkũũri witũ, Jesũ nĩegũũka rĩngi.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Gambia irĩma-inĩ ona ndihũ-inĩ, Jesũ nĩegũũka rĩngi,\n" +
                "Na ngurunga-inĩ, mũgambie coro, Jesũ nĩegũka rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mĩnyamaaro nĩ ĩgũtũmenyithia, Jesũ nĩegũũka rĩngĩ,\n" +
                "Na ndũrĩrĩ nĩirokanĩrĩra, Jesũ nĩegũũka rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Ndũũrĩrĩ  nĩmbucunu na nĩguo, Jesũ nĩegũũka rĩngĩ,\n" +
                "ũũgĩ nĩ mũingĩ ona mĩhang’o, Jesũ nĩegũũka rĩngĩ\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("210. Muthenya Wa Gucooka Kwa Jesu (NZK 164)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũthenya wa gũcooka kwa Jesũ nĩũkuhĩrĩirie,\n" +
                "Akoongania andũ ake  amatware gwake.\n" +
                "\n" +
                "\t\n" +
                "Nĩtũroona mĩrũri ya ũtheri ĩmũrĩkĩte nduma;\n" +
                "Nĩtũroona mĩrũri ya ũtheri wa kũriũkia akuũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũhoro mwega nĩũrahuunjĩrio Ndũrĩrĩ ciothe;\n" +
                "Mũhikania nĩagaacooka Na coro wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ agaacooka na araika aingĩ ma,\n" +
                "Atwaare atheru igũrũ moorĩre gĩkuũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Endwa arĩa maakomire nĩmakoonwo rĩĩngĩ,\n" +
                "Arĩa mararĩra Nĩmakaagirio maithori.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("211. Kiiriro Kiega Kia Eetikia (NZK 165)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kĩĩrĩro kĩega, kĩa eetĩkia, one nĩndĩrooka o naruo mũno.\n" +
                "Kũrĩ na ũgwati, rũũgama wega, ndũkagoonderere wee no wĩhũũge.\n" +
                "\n" +
                "\t\n" +
                "We menyerera wĩtĩkio waku,\n" +
                "Nĩũkaheeo itũũra rĩerũ,\n" +
                "Gĩũke ũtoone gĩkeno-inĩ,\n" +
                "Kĩrũũgame wega, ũheo thũũmbĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩtũikare, tũkĩhooyaga, amu aingĩ nĩmakahũbanĩrio\n" +
                "Nĩtũũĩ atĩ e hakuhĩ gũũka mũthenya tũtiũĩ, nĩ ũrĩkũ angĩũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Twĩrĩgĩrĩire, kiugo gĩake, Kĩrĩa kĩrahuunjia gũũka gwake;\n" +
                "Mwĩhoko witũ nĩ kĩĩranĩro, Nĩngũũka o na rua, rũũgama wega.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("212. Tutiui Hindi Iria Angiuka (NZK 174)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũtiũĩ hĩndĩ ĩrĩa angĩũka,\n" +
                "ũtukũ kana mũthenya,\n" +
                "Hihi nĩ mĩarahũko-inĩ,\n" +
                "Kana nĩ rũciinĩ tene,\n" +
                "Na aratwĩra twĩhaarĩrie,\n" +
                "Twakie matawa maitũ,\n" +
                "Nĩgeetha hingo ĩrĩa angĩũka.\n" +
                "Tũkorũo tũmwetereire.\n" +
                "\n" +
                "\t\n" +
                "Tũ mwetereire (Nowe tũikarĩte twetereire)\n" +
                "Tũ_mweterei_re,\n" +
                "(Nowe tũikarĩte twetereire)\n" +
                "Nowe wiki, twetereire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩngũririkana tha ciake,\n" +
                "Nowe ũhonokio witũ:\n" +
                "Aatigire mũciĩ wĩ riiri,\n" +
                "Nĩguo akuĩre andũ oru.\n" +
                "Na nĩnguona nĩkwagĩrĩire\n" +
                "Na ithuĩ tũrĩ andũ ake,\n" +
                "Tũkionania wendo ũcio witũ,\n" +
                "Tũkorũo tũmwetereire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Wee Jesũ Mũhonokia mwendwa,\n" +
                "Nĩũũĩ nĩmenyereire,\n" +
                "Kĩĩrĩgĩrĩro gĩa gũkuona,\n" +
                "Na gĩa gũũka kũu gwaku,\n" +
                "ũgĩũka kũrĩ andũ arĩa angĩ,\n" +
                "ũkĩmatuĩra ciira,\n" +
                "Niĩ ngakorũo ndĩ waku,\n" +
                "Njikarĩte o ngwetereire.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("213. Gikeno Guuku Thi Guothe (NZK 166)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Gĩkeno gũũkũ thĩ guothe, Jesũ nĩegũcooka!\n" +
                "Mũndũ o wothe amwĩhoke, Mũndũ o wothe amwĩhoke\n" +
                "Ciũmbe inai, ciũmbe inai, ciũmbe ciothe inai.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na Jesũ nĩ Mũthamaki: Andũ nĩ mainei;\n" +
                "Mĩtĩ, irĩma, na mahiga: Mĩtĩ, irĩma, na mahiga,\n" +
                "Onaniai gĩkeno, Onaniai gĩkeno, onaniai gĩkeno kĩnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩagathamaka wega ma: Na nĩakarutana,\n" +
                "Ndũũrĩrĩ ũthiingu wake, Ndũũrĩrĩ ũthingu wake,\n" +
                "Wendo mũnene ma, wendo mũnene ma, wendo mũnene ma wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Gũtigakorũo na meehia: Thĩ-inĩ wa thĩ njerũ, Jesũ nĩwe Mũthamaki,\n" +
                "Jesũ nĩwe Mũthamaki, ũrĩa ũgaathana, ũrĩa ũgaathana,\n" +
                "O kũu thĩ-inĩ wa thĩ njerũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("214. Thikiriria Makiina");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Thikĩrĩria, makĩina, Araika igũrũ;\n" +
                "Rũĩmbo rwega rũthaka, rũa gũciaruo kwa Jesũ;\n" +
                "Kenai andũ a thĩ hamwe na araika;\n" +
                "Twanĩrĩre na araika, “Kristo nĩmũciare”\n" +
                "Araika mekũina, “Nĩakumio Mũthamaki”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kristo Mwathani igũrũ, Nĩ wa tene na tene;\n" +
                "Nĩokire agĩciarũo, Nĩ mũirĩtu mũthiingu;\n" +
                "Nĩ Ngai wĩ na mwĩrĩ, Nĩakumio Mũthamaki,\n" +
                "Nĩatũũranirie na andũ, Jesũ We Immanuel!\n" +
                "Araika mekũina, “Nĩakumio Mũthamaki”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩagoocwo Mũthamaki, Wa Thaayũ na ũthiingu!\n" +
                "We nĩ ũtheri na muoyo, O na kũhonia andũ;\n" +
                "Akĩima ũnene, okire tuone muoyo,\n" +
                "Akũũrire andũ a thĩ, Nĩguo maciarũo rĩĩngĩ;\n" +
                "Araika mekũina. “Nĩakumio Mũthamaki”.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "ũka narua Mwathani, Nĩtũgũkwenda mũno;\n" +
                "Ikara na ithuĩ Kristo, ũtũteithie mbaara-inĩ;\n" +
                "Riinga shaitani kĩongo, ũtherie ngoro ciitũ,\n" +
                "Tũhaanane o Nawe, ũrokumio Mwathani”.\n" +
                "Araika mekũina, “Nĩakumio Mũthamaki.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("215. Onei Agiuka Riera-ini");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Onei agĩũka rĩera-inĩ, Jesũ ũrĩa woragirũo,\n" +
                "Andũ atheru metereire, Oke amatware gwake,\n" +
                "Halleluyah! Halleluya! Jesũ nĩwe Mũthamaki,\n" +
                "Halleluyah! Halleluya! Jesũ nĩ Mũthamaki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "ũhonokio wanagĩũka, ũtũire wetereirũo,\n" +
                "Andũ a Ngai arĩa mathũirũo, Makaiyũkio rĩera-inĩ\n" +
                "Arata aitũ, arata aitũ, mũthenya rĩu nĩ mũkĩnyu.\n" +
                "Arata aitũ, arata aitũ, mũthenya nĩ mũkinyu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Coro warĩkia kũhuhwo matu na thĩ cieherio,\n" +
                "Arĩa othe mamũthũire makagirĩka mũno,\n" +
                "Makeragwo, Makeragwo, rĩu nĩmũgũcirithio,\n" +
                "Makeragwo, makeragwo, ũkai mũcirithio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Andũ othe nĩmakamuona e na ũkengi mũingĩ,\n" +
                "Arĩa mamũthecangire makĩmwamba mũtĩ-inĩ,\n" +
                "Makarĩra, makarĩra, mona agĩũka rĩera-inĩ,\n" +
                "Makarĩra, makarĩra, mona agĩũka rĩera-inĩ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("216. Angigooka Riri Ruoro Rutemaga (NZK 163)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Angĩgooka rĩrĩa ruoro rũtemaga\n" +
                "Kwarĩkia gũkia, oke akeengete ma,\n" +
                "Na riiri mũingĩ, nĩagatwamũkĩra na tũkene\n" +
                "Tũkĩambata gwake.\n" +
                "\n" +
                "\n" +
                "Jesũ nĩ nginya rĩ, Tũgũgweterera hihi,\n" +
                "Ndũkĩhiũhe, Halleluya,\n" +
                "Halleluya! Amen. Halleluya! Amen.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Angĩgooka kwĩ mũthenya barigici\n" +
                "Kana hihi oke gũtukĩte mũno,\n" +
                "Tuone ũtheri ũhaana ta mũthenya mũnene\n" +
                "Tũkĩambata gwake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Tũkaigũa rũĩmbo, Hosana Mũthamaki,\n" +
                "Rũa andũ atherũ hamwe na araika,\n" +
                "Nake Mwaathani akengete gũkena mũno-ma.\n" +
                "Tũkiambata gwake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Tũkahuungura matu mairũ na njata\n" +
                "Gĩkuũ na thĩna, o na mĩrimũ yothe.\n" +
                "Kũmenwo, ruo, kĩrĩro nĩigathira o kũna\n" +
                "Tũkĩambata gwake\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("217. Nieguuka O Riingi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Nĩegũũka o rĩngĩ, aiyĩre andũ ake,\n" +
                "Amatware gwake mũciĩ matũũranie nake.\n" +
                "Roria maitho igũrũ, ũkĩinaga nyĩmbo,\n" +
                "Egũũka o ta Mũnene atũhonokie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Thĩ nĩĩgathingitha, na matu mathengio\n" +
                "Mũndũ mwĩhia akarora kũ nĩũndũ wa kũmaka,\n" +
                "Ngũũria ũgeka atĩa waaga gwa kwĩhitha.\n" +
                "Mwathani nĩakerĩhĩria tha ciake ciaregwo\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " Arĩa matamũũĩ nĩmakamũmenya,\n" +
                "Maitho make no ta mwaki harĩ mũndũ mwĩhia,\n" +
                "Arĩa mamũthũire na mamũcambagia,\n" +
                "Makĩmenaga andũ ake makarĩra tũhũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("218. Kai Jesu Ni Mwega");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kaĩ Jesũ nĩ Mwega, Atwendete ũũ!\n" +
                "Nĩomire Igũrũ agĩũka gũũkũ,\n" +
                "Nĩathecirũo nĩ andũ na nĩaathĩĩnire,\n" +
                "Akĩũragwo tondũ wa niĩ nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na nĩendaga mũno tuoherũo Meehia;\n" +
                "O ũrĩa werira akamũiyũkia,\n" +
                "Na ndangĩte Mũndũ ũmwĩtĩkĩtie,\n" +
                "No amũteithagia O hingo ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ na arĩa akũũre me ndũgũ mũno;\n" +
                "We nĩamatoongagia ngoro-inĩ ciao;\n" +
                "Mũtaangĩri wao na Mũrĩithi nowe;\n" +
                "O kĩrĩa maagaga We akamahe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ithuothe twĩtĩkie Jesũ O rĩu!\n" +
                "Andũ araathime no andũ ake;\n" +
                "Na rĩu tũrĩ muoyo no atwendaga,\n" +
                "Na ningĩ twakua nĩagatũriũkia.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("219. Jesu Muhonokia (NZK 167)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ Mũhonokia atũũraga muoyo,\n" +
                "Niĩ nĩ njũũĩ atĩ arĩ hamwe na niĩ,\n" +
                "Njiguaga mũgaambo ngaiguĩrũo tha nĩwe,\n" +
                "Akoragwo na niĩ hĩndĩ ciothe.\n" +
                "\n" +
                "\t\n" +
                "Ee ho, Ee ho Ee muoyo Mwathani,\n" +
                "Aceeraga, na akaria na niĩ hĩndĩ ciothe,\n" +
                "Ee ho,Ee ho aheane muoyo!\n" +
                "Na nĩ njũũĩ ũguo nĩguo, Ee ho thĩ-inĩ wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũgitĩri wake ũroneka o wega,\n" +
                "Magũrũ o na maanoga, ndingĩkua ngoro,\n" +
                "Na nĩangiragia ngoima kĩhuuhũkanio-inĩ,\n" +
                "Na rĩrĩa agacooka nĩngamuona.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("220. Niwokire Tondu Wakunyeenda");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩwokire tondũ wakũnyeenda, ũgĩtiga ũnene Waku,\n" +
                "Bethlehemu nĩ kũũndũ kũũrũ, Ndũngĩona hagũikara.\n" +
                "\n" +
                "ũka thĩinĩ wakwa, Jesũ, Ngoro-inĩ ĩĩ na thaayũ.\n" +
                "ũka thĩinĩ wakwa, Jesũ, Ngoro-inĩ ĩĩ na thaayũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Waaciarirũo ũgĩtuĩka mũndũ, Na araika nĩmaainire;\n" +
                "ũkĩaga nyũũmba ũgĩikara na ng’ombe; Wee mũũmbi wa thĩ yothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mbwe nĩraaraga imaamo-inĩ, Na nyoni ii na itara,\n" +
                "No Kristo wa Ngai ndaarĩ na ha gũikara, Aaraaraga gĩthaka-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "ũgĩũka Jesũ, kũhonokia andũ, Nĩguo matuĩke aku;\n" +
                "ũkĩmenwo na ũkĩhũũrũo, ũgĩitwo thakame.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("221. Coro Wa Ngai Ukahuuhwo (NZK 168)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Coro wa Ngai ũkahuhwo, Mũthenya wa ithirĩro\n" +
                "Atheru a Ngai nĩmakonganio\n" +
                "Marĩĩtwa magĩtaanwo niĩ ngakorwo nĩho ndĩ\n" +
                "Nĩguo tũgatwarwo igũrũ kwa Jesũ \n" +
                "\n" +
                "\n" +
                "Marĩĩtwa nĩmagetanwo!\n" +
                "Marĩĩtwa nĩmagetanwo!\n" +
                "Marĩĩtwa nĩmagetanwo!\n" +
                "Marĩĩtwa magĩtanwo ngakorwo ho\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mũthenya ũcio wĩna riiri, rĩrĩa akuũ magatirikio\n" +
                "Nĩmakoima mbĩĩrĩra-inĩ cia\n" +
                "Andũ ake arĩa atheru amainũkie igũrũ\n" +
                "Marĩĩtwa magĩtanwo ngakorwo ho\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩtũrute wĩra wake twĩna kĩo kĩnene\n" +
                "Tũheana ũhoro ũcio wake \n" +
                "Na twarĩkia wĩra wake narĩo ihinda rĩthire\n" +
                "Marĩĩtwa magĩĩtanwo ngakorwo ho\n" +
                "\t\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("222. Ndingicaria Utoonga (NZK 170)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndingĩcaria ũtoonga, kana indo cia thĩ;\n" +
                "Nĩngwenda kũheeo indo cia igũrũ,\n" +
                "Nĩngenaga ndaigwa, atĩ rĩĩtwa rĩakwa,\n" +
                "Nĩrĩandĩkĩtwo wega, ibuku-inĩ rĩake.\n" +
                "\n" +
                "\t\n" +
                "Rĩĩtwa rĩakwa rĩĩ ho,\n" +
                "Mbuku-inĩ ya muoyo,\n" +
                "Handũ heega igũrũ,\n" +
                "Rĩĩtwa rĩakwa rĩĩ ho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Meehia makwa maingĩ, matingĩtarĩka,\n" +
                "ĩndĩ thakame yake nĩĩnjiganĩte,\n" +
                "Nĩnjĩrĩĩtwo nĩ Jesũ, atĩ ũũru wakwa,\n" +
                "Atheretie ngathera, ngathera o ta ira.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mũciĩ ũcio nĩ mwega, kũrĩ mĩciĩ ya thĩ,\n" +
                "Gwĩ gĩkeno kĩnene kĩa andũ ake,\n" +
                "Arĩa mahonoketio, nĩ Jesũ Kristo,\n" +
                "Rĩĩtwa rĩakwa e na rĩo, mbuku-inĩ ya muoyo.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("223. Muthamaki Niatwitite (NZK 172)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũthamaki nĩatwĩtĩte, tũtonye iruga-inĩ rĩake;\n" +
                "Gũgakĩhana atia hĩndĩ iyo Mũthamaki oka?\n" +
                "\n" +
                "\n" +
                "Mũrũ-wa-iya Jesũ oka, gũkahana atia\n" +
                "Tũkahaana atia acoka, wee ona niĩ?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na nĩagoka e na ũnene, ekĩrwo thũmbi na ti mĩigua,\n" +
                "Titherũ ũnene nĩ ũkonwo Mũthamaki oka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Akametĩkĩra akenete, arĩa me na ngũo cia ũhiki;\n" +
                "Tũkarathimwo twamũkenia mũthamaki oka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩgũkagia na kwamũranio, aria aregi nĩ makarĩra;\n" +
                "ĩo nĩ hĩndĩ ya kĩmako Kristo acooka.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Mwathani rĩu tũraathime, arĩa rĩu tũgwetereire;\n" +
                "Tũtigetigĩre gũkuona, woka mũnene.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("224. Ciira Niwambiriirio Iguru");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ciira nĩwambĩrĩirio Iguru; tukaaruugama atĩa he Ngai?\n" +
                "Ngai nĩagatutuira ciira, kuringana na ciĩko.\n" +
                "\n" +
                "\n" +
                "Tukaaruugama atĩa ithuothe, muthenya ucio munene,\n" +
                "Meehia maitu nĩmakeherio, kana nĩmagaatuguithia?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ciira wambĩrĩirio na andu arĩa akuu, Gucookerwo arĩa me muoyo,\n" +
                "No matuĩro makoonekana, Mabuku-inĩ ya Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tukaruugama atĩa muthenya ucio, Hitho ciitu, mundu o mundu,\n" +
                "Ikĩonekana mbuku-inĩ? Jesu tuciirĩrĩre.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("225. Tutiui Thaa (NZK 173)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũtiũĩ thaa, iria Jesũ arĩũka\n" +
                "Thengia marũũri, macio nomo tũrona,\n" +
                "Nake nĩegũũka, ahingie ciĩranĩro, No thaa tũtoĩ.\n" +
                "\n" +
                "\t\n" +
                "Nĩegũũka nĩtwĩhũge thaa ciothe,\n" +
                "Nĩũgũũka, Haleluya, Haleluya,\n" +
                "Egũũka na matu, na ũkengi wa Ngai,\n" +
                "No thaa tũtoĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mũndũ ũrĩa mũũgĩ, e mũheere wa ũhoro,\n" +
                "Ndũrore Mbuku, ũguũrĩrio maũndũ\n" +
                "ũrathi wothe, githĩ to ũrahinga, No thaa tũtoĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Rĩu nĩtũhoe, na tũgwatie matawa,\n" +
                "Ningĩ twĩhũge tũkĩrutaga wĩra,\n" +
                "Na rĩrĩa arĩũka tũtikorũo nduma-inĩ, no thaa tũtooĩ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("226. Nguuka Ndige Nduma Na Kieha");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngũũka ndige nduma na kĩeha, ngũũka gwaku, gwaku Jesu\n" +
                "Unjohore nyone utheri, nguuka gwaku Jesu\n" +
                "Ndĩ muruaru we uhe hinya, ukĩa wakwa nĩukuninĩra,\n" +
                "Ndige mehia njuke harĩ we, nguuka gwaku Jesu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Thoni ciakwa o na ũitangi, nĩngũcite, njũke gwaku\n" +
                "Mũtharaba waku ũndongie, ngũũka gwaku Jesũ.\n" +
                "Ngũtiga kĩeha ũhe thayu, ndige gwĩtanga ũhuurũkie,\n" +
                "Ndige ruo nyinage rũĩmbo, ngũũka gwaku Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndige ngũrũ o na mwĩtĩyo, ngũũka gwaku, gwaku Jesũ\n" +
                "ũnjathage na ũndathime, ngũũka gwaku Jesũ.\n" +
                "Ndige kwĩigania ngwĩhoke, ndige thĩna ndĩkenagĩre,\n" +
                "Nyũmbũkage ngorĩra thĩna, ngũũka gwaku Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ndige guoya wa mbĩrĩra-inĩ, ngũũka gwaku, gwaku Jesũ\n" +
                "ũnyonie ũtheri wa gwaku, ngũũka gwaku Jesũ.\n" +
                "Ndige kwanangĩka gũtheri, Ndonye thayũ-inĩ kiugũ giaku.\n" +
                "Ndĩroragĩre ũthiũ waku, ngũũka gwaku Jesũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("227. Riria Jesu Agacookereria (NZK 169)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Rĩrĩa Jesũ agacokereria Ndũrĩrĩ mbere yake,\n" +
                "Ciira witũ nĩũgatuo wega Kana tũgatuĩrũo ũũru?\n" +
                "\n" +
                "\n" +
                "Nĩagaita ngano yothe ikũmbĩ-inĩ Mahuti magaikio riiko.\n" +
                "Ciira witũ nĩũgatuwo wega Andũ rĩrĩa makariũkio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩtũkaigua mũgambo wa Jesũ , Twĩtwo ‘ngombo’ njathĩki\n" +
                "Kana nĩ kũinaina tũkainaina Tũthengio gĩtĩ-inĩ gĩake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩagatheka ona ciana ciake Aciona na rũũri rũake,\n" +
                "Iheo ũgemu wa matu-inĩ iturĩtie ndu harĩ we.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩ wega twĩhũge twetereire Tũgwatie matawa maitũ,\n" +
                "Mũhikania etana iruga-inĩ Tũtonyanie hamwe nake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Ngoro ciitũ nĩirore matu-inĩ Na tũkirĩrĩrie thĩna,\n" +
                "Na twanina rũgendo o wega Tũgathiĩ hakuhĩ nake.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("228. Twi Na Ciikaro Matu-ini");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Twi na ciikaro matu-ini, Jesu niatwirire,\n" +
                "Niarathindekera aria mamwihokete,\n" +
                "Riu mahinda ma nduma (nduma) turi athiini a thi,\n" +
                "Thutha-ini tgathii gwake, na tutuike itonga.\n" +
                "\n" +
                "\n" +
                "(Hari Jesu, tuikare hamwe nake)\n" +
                "(Gikeno kiingi) niguo tukene tene na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Riu hindi ya mathiina, o na kio kieha giitu.\n" +
                "Tumwihokage Mwathani ni Muhonokia witu;\n" +
                "Utheri uingate (Niukeheria) nduma,tuikarage na thaayu\n" +
                "Jesu niatwetereire tuthii gwake iguru.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesu witu nieguuka, wira wa thi uniinwo,\n" +
                "Muthenya wi hakuhi ma, na niguo twetereire,\n" +
                "Kwoguo rekey twiigue, hindi iria agooka,\n" +
                "Atukore twihariirie guthii nake matu-ini.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("229. Nitukonana Na Jesu (NZK 175)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩtũkonana na Jesũ, ũthiũ kwa ũthiũ nĩguo,\n" +
                "Hĩndĩ ĩrĩa akonithanio, Nĩngamuona Mũkũri.\n" +
                "\n" +
                "\t\n" +
                "Nĩtũkonana na maitho, kũu gwitũ matu-inĩ,\n" +
                "Kũu mwago-inĩ wake, nginya tene na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Rĩu ndĩmuonaga na mairia, ti ũrĩa atariĩ kũna;\n" +
                "No hĩndĩ ĩrĩa agacoka, nĩngamuona wothe biũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Maithori hamwe na thĩna, nĩikaninwo ciothe biũ,\n" +
                "Ithua nĩikarũngario, mũtumumu agĩe maitho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "ũthiũ na ũthiũ o kũna, hau gĩkeno gĩtheri,\n" +
                "Ngakena mũno mũno ma, rĩrĩa ngona Mũkũũri\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("230. Tugaacemania Ruui-ini");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũgaacemania rũũĩ-inĩ maaĩ mega ma matu-inĩ,\n" +
                "Maroima hau ũkeengi-inĩ, hakuhĩ na gĩtĩ kĩa Ngai.\n" +
                "\n" +
                "\n" +
                "Ti (tũgomane) rũũĩ-inĩ Atheru othe gwitũ nĩ rũũĩ-inĩ!\n" +
                "Tũgaacemania rũũĩ-inĩ, hakuhĩ na gĩtĩ kĩa Ngai.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tũgaacemania rũũĩ-inĩ, na Jesũ Mũrĩithi witũ.\n" +
                "Tũkamũhooyaga mũno, ũthiũ wake wĩ mbere iitũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũgĩthiĩ hau rũũĩ-inĩ, tũtige maũũru mothe,\n" +
                "Wega wa Ngai woneke, wĩ na nguo na thũũmbĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Rũũĩ rũkengeete mũno, kwa Jesũ no ta gĩciicio,\n" +
                "Kũu tũtigatigana, tũkĩmũkumia na Nyĩmbo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Rũũĩ rwĩ hakuhĩ mũno, twĩ hakuhĩ gũkinya kuo,\n" +
                "Tũtoonye gĩkeno-inĩ na thaayũ ũtagaathira.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("231. Wira Wakwa Wathira (NZK 177)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Wĩra wakwa wathira, ndarĩkia kũhonokio\n" +
                "Na heo mwĩrĩ ũrĩa ũtagakua,\n" +
                "Nĩngamenya Mũkũũri ndakinya kũu igũrũ,\n" +
                "Agatuĩka wa mbere kũndangĩra.\n" +
                "\n" +
                "\n" +
                " Kũmenya nĩ ngamũmenya, ahonokia tũikarage nake,\n" +
                "Kũmenya nĩ ngamũmenya, ndona irema cia mĩcumarĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩngagĩa na gĩkeno ndamuona ũthiũ wake,\n" +
                "Onaguo ũkengi wa maitho make,\n" +
                "Nĩngakumia Mũkũũri tondũ wa tha na wendo,\n" +
                "Icio iheete handũ kũu igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩngatonya kĩhingo ndĩ na nguo igũkenga,\n" +
                "Anginyie kũu gũtarĩ maithori,\n" +
                "Na nĩngaina rũĩmbo rũa tene na tene kũna,\n" +
                "No nĩngenda nyone Mũkũũri mbere.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("232. Uka Kwi Jesu Ndukaarege (NZK 99)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "ũka kwĩ Jesũ ndũkaarege, ũka ũmũthĩ ũmwĩtĩkie,\n" +
                "Tondũ arĩ haha Jesũ mwene, Agĩkwĩra, “ũka”\n" +
                "\n" +
                "\n" +
                "Tũgakena mũno mũno ma, Twagirio gũcooka kwagana,\n" +
                "Tũtũũre nake Mũhonokia, Tene o na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũka kwĩ Jesũ ndũkaarege, Reke Ibuku rĩgũtaare,\n" +
                "Nĩrĩo rĩandĩke njĩra yake, Nake ekuuga “ũka”.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "ũka kwĩ Jesũ ndũkaarege, igua mũgaambo ũcio wake,\n" +
                "Rĩu ũmwĩtĩkie ũgĩkene, Tondũ ekuuga “ũka”.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "ũka kwĩ Jesũ ndũkaarege, Ngai nĩekwenda agũciare,\n" +
                "Na ũndũ wa Jesũ akuohere, Nake ekuuga “ũka”\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("233. Murimo Uria Wa Jordani (NZK 178)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũrĩmo ũrĩa wa Jordani , nĩkuo ndĩroreire\n" +
                "Bũrũri mwega Kaanani, nĩkuo njerekeire\n" +
                "\n" +
                "\n" +
                "Tũgatũraga hamwe na Jesũ\n" +
                "Bũrũri ũcio mwega wa gĩkeno\n" +
                "Nĩtũkaina rwĩmbo rwa Musa na Jesũ\n" +
                "Tũkaina tene na tene\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Bũrũri ũcio wĩna ũtheri, ũtheri ũtagathira \n" +
                "Kristo nĩwe riũa riakuo nĩaingatĩte nduma\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Hihi nĩrĩ ngakinya kuo, njĩtwo mũrathime?\n" +
                "ũthamaki-inĩ wa Baba, na ndĩmwone ũthiũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Gĩkeno gĩakwa ngoro-inĩ ,nĩgũka kũiyĩrwo\n" +
                "Ndingĩtigĩra makũũmbĩ, moothe ma Jordani.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("234. Hindi Iria Wira Ugaathira (NZK 179)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Hĩndĩ ĩrĩa wĩra ũgaathira, Nao agethi mainũkie,\n" +
                "O mũndũ magetha make, kũu Jerusalemu.\n" +
                "\n" +
                "\n" +
                "Gĩkeno nĩgĩkaingĩha, Gĩkeno gĩtagaathira,\n" +
                "Gĩkeno kĩa mũthenya ũcio, Agethi mainũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mũthenya ũcio nĩtũkaina, Na tũgĩcookagia ngaatho,\n" +
                "He Kristo Mũnene witũ kũu Jerusalem.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ikeno nyingĩ nĩtũkoona, itũũro-inĩ cia matu-inĩ,\n" +
                "Jesũ nĩwe mũthoondeki, kũu Jerusalemu.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("235. NiKuri Bururi Mwega Ma (NZK 180)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩkũrĩ bũrũri mwega ma, na tũngĩtĩkia twawona,\n" +
                "Amu Ngai nĩatũire o kuo, atũthondekagĩra mũciĩ.\n" +
                "\n" +
                "\n" +
                "Igũrũ kwa Jesũ, nĩtũgacemania o kuo,\n" +
                "Igũrũ kwa Baba, nĩtũgacemania o kuo.\n" +
                "\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩtũkaina nyĩmbo njega, iria cia andũ atheru,\n" +
                "O na mo maroho maitũ, matikaigua ruo rĩngĩ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩtũgathage Ithe witũ, tũmũcokagĩrie mũhera,\n" +
                "Tondũ nĩ wendo wake mwene, ũtũmaga tũkenagio.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("236. Ngatuura Ruui-ini (NZK 181)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Ngatũũra rũũĩ-inĩ rũa muoyo, Rũrĩa rũkengete,\n" +
                "Harĩa Jesũ atwetereire Atũkũngũĩre.\n" +
                "\n" +
                "\n" +
                "Ngatũũra handũ he na maĩ, maĩ marĩa me na muoyo,\n" +
                "Njikaranagie na Jesũ, Nĩwe maĩ ma muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Rĩrĩa twanogio nĩ rũgendo Nĩtũhurũkage,\n" +
                "Harĩa he na maĩ ma muoyo, ũnyue ũhonoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "We ũnyotie ũka kwĩ Jesũ nĩegũkũnyotora,\n" +
                "Jesũ nĩ maĩ ma muoyo, ũnyue ũhonoke.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("237. Kuu Gutitukaga (NZK 182)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kũu  gũtitukaga kwĩ na mũciĩ mwega,\n" +
                "Naguo wĩna mĩromo ikũmi na ĩĩrĩ.\n" +
                "\n" +
                "\t\n" +
                "Tũkagirio maithori, gikuũ nĩgĩgathira,\n" +
                "Ruo na guoya nĩikora, Na gũtitukaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mĩrango nĩ ya ruru Nĩ mũciĩ mũthaka,\n" +
                "Njĩra nĩ cia thahabu, Na gũtitukaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mĩrango ndĩhingagwo, Ya gũtonya thĩinĩ,\n" +
                "Kwĩ na rũũĩ rũa muoyo, Na gũtitukaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Kũu gũtirĩ riũa Jesũ nĩwe riũa,\n" +
                "Na nĩ kũndũ gũthaka, Na gũtitukaga.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("238. Magerio Maathira Ndiikie Wira");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Magerio mathira ndĩkie wĩra, Ndakinya murĩmo kurĩa kwega,\n" +
                "Njikaranagie na Jesu wakwa Ngamugocaga mĩndĩ na mĩndĩ\n" +
                "\n" +
                "\n" +
                "Ndamugoca hĩndĩ ciothe, Hĩndĩ ciothe, hĩndĩ ciothe,\n" +
                "Anjĩtĩkĩra ngamuona uthiu, Ngamugocaga, mĩndĩ na mĩndĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Angĩkanyonera handu iguru, Nĩgetha ngamuona uthiu wake,\n" +
                "Nĩngakenera wega ucio wake, Ngamugocaga mĩndĩ na mĩndĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Arata akwa arĩa twatiganire Ngamona kuo makenete muno,\n" +
                "Na ndona Mwathani agĩtheka, Ngamugocaga mĩndĩ na mĩndĩ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("239. Twaragia Uhoro Mwega");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Twaragia ũhoro mwega Wa mũciĩ mũthaka mũno,\n" +
                "Riiri wakuo nĩmuumbũre ĩĩ hihi nĩngatonya kuo.\n" +
                "Njĩra ciakuo no thahabu, Thingo cia tũhiga twega\n" +
                "Ikeno ciakuo tũtiũĩ ĩĩ hihi nĩngatonya kuo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Andũ akuo matiĩhagia Kĩeha gũtirĩ na thĩna\n" +
                "Wa ngoro o na wa mwĩrĩ ĩĩ hihi nĩngatonya kuo.\n" +
                "Wendani nĩ mũingĩ, Namo matonyo nĩ matheru ma,\n" +
                "Nĩguo kanitha wa mbere, ĩĩ hihi nĩngatonya kuo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Njagĩria ngoro ĩthere Hotage magerio mothe\n" +
                "Nĩguo naniĩ ngemenyera Wega ũrĩa o monaga\n" +
                "Ngaceeraga mĩgũnda-inĩ Nyone ũgooci wa Ngai,\n" +
                "Twĩ hamwe na araika ĩĩ hihi nĩngatonya kuo.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("240. Ningeekira Thuumbi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩngeekĩra thũũmbĩ ndakinya igũrũ,\n" +
                "Thũũmbĩ ĩo nĩ ya thahabu njega,\n" +
                "Ndaiga mĩrigo thĩ heeo ũthingu wake,\n" +
                "Nĩngainĩra Jesũ Mũhonokia.\n" +
                "\n" +
                "\n" +
                "Ndathiĩ igũrũ, ndathiĩ igũrũ,\n" +
                "Kĩeha o na mathĩĩna nĩ igathira,\n" +
                "(Ndathiĩ igũrũ) ndathiĩ igũrũ\n" +
                "Kĩeha nĩgĩgaathira ndathiĩ igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nduuma nĩĩgathira ndakinya mũciĩ,\n" +
                "Nĩngona ũtheri ndakinya igũrũ,\n" +
                "ũtheri wa igũrũ, ũmũrĩkagĩre,\n" +
                "ũndongorie kinya nginye mũciĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩngoona ũthiũ wa Jesũ Mwathani,\n" +
                "Hĩndĩ ĩria ngakinya kũu matu-inĩ,\n" +
                "Ndũũgame he Jesũ, ndĩ-mũturĩrie ndu,\n" +
                "Ngamũgoocaga tene na tene.\n" +
                " \n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("241. Mucii Wi Na Wendani (NZK 184)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mucii wina wendani ni mwega muno,\n" +
                "Gutari matetania ni kwega muno,\n" +
                "Buthi hamwe na thayu irehagwo ni wendo\n" +
                "Matuku magathira kwi na gikeno.\n" +
                "\n" +
                "\n" +
                "Kwendana, ni hitho, Ya kuninaga thina, \n" +
                "Hingo ya muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Mucii niurigemaga kwagia wendani,\n" +
                "Rumena o na uiru ciagithio mweke.\n" +
                "Mucii niugemio wega uhane ta mahua,\n" +
                "Matuku mathirage kwi na gikeno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Aria othe me iguru nimakenaga,\n" +
                "Mona mucii wendaine, wi na gikeno,\n" +
                "Thi yothe niikenaga micii yagia wendani,\n" +
                "Muumbi witu o nake no akenaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Jesu utwamukire tuhe wendani,\n" +
                "We niwe igongona, tuhe wendani,\n" +
                "Tugitire mogwati, na tutige kwihagia\n" +
                "Utuiguire tha Jesu, tuhe wendani.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("242. Ngai Wi Wendo Mukinyaniru (NZK 185)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngai wĩ wendo mũkinyanĩru ki, ũkĩrĩte ũmenyo wa andũ;\n" +
                "Nĩtwenyihia ithuĩ tũtirĩ na hinya, ũhe endwa aitũ kĩraathimo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Wee wĩ wendani ũtangĩthimĩka, nĩtwamahooera gĩtĩ-inĩ gĩaku;\n" +
                "ũmahe wendo ũtangĩtuĩkana, Aya mahikania mbere yaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Wee muoyo na wĩ mũrũgamĩrĩri, wa wĩtĩkio na kĩĩrĩgĩrĩro;\n" +
                "Haha kĩmahe ũkirĩrĩria, nĩguo thutha gũtikagĩe ruo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mahe gĩkeno kĩrĩa gĩa kũhota, gwĩkĩra ũtheri kwagĩa kĩeha;\n" +
                "Mahe thaayũ ũcio ũbataranagia, rĩrĩa kwagĩa na mathĩĩna thĩ ĩno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Reke mathiĩ na mbere gũteithania, mũtũũrĩre-inĩ wao gũũkũ thĩ;\n" +
                "Nĩguo thuutha-inĩ rĩrĩa Jesũ agooka, magaakenaga tene ne tene.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("243. Riria Tuonana Na Araata (NZK 186)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Rĩrĩa tuonana na araata, nĩtũkenaga ma,\n" +
                "No atĩ no nginya thuutha-inĩ tũtigane rĩĩngĩ.\n" +
                "\n" +
                "\n" +
                "Tũtigatigana rĩĩngĩ mũciĩ wa igũrũ,\n" +
                "Bũrũri mwega Igũrũ, Tũtigatigana.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Twĩrĩgĩrĩire tũkenete, Kuonana Igũrũ,\n" +
                "Twarĩĩkia kũhootana nĩtũkoona araata aitũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Gũtirĩ kiugo gĩa gũtigana, gĩkaario o rĩ,\n" +
                "Nĩtũkainaga hĩndĩ ciothe Nyĩmbo cia gĩkeno.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("244. Gukoma Hamwe Na Jesu (NZK 187)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Gũkoma hamwe na Jesũ! Nĩ toro wa kĩraathimo,\n" +
                "Gĩkeno gĩtangĩthira, na gĩtangĩthengio nĩ thũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Gũkoma hamwe na Jesũ! Nĩ kũgĩa kĩĩrĩgĩrĩro.\n" +
                "Gĩa kũriũkio na kũheeo, muoyo wa tene na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Gũkoma hamwe na Jesũ! Nĩ thaayũ wa arĩa makariũkio;\n" +
                "Mũthenya ũcio nĩ makoona ũnene wa Mwathani.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Gũkoma hamwe na Jesũ! Makaariũkio mbĩĩrĩra-inĩ;\n" +
                "Makaahingũra mbĩĩrĩra, moime na ũnene mũingĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("245. Nindionerire Githima (NZK 188)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩndĩyoneire gĩthima Nakĩo nĩ gĩtune,\n" +
                "Nĩ kĩriku na nĩ kĩariĩ Na nĩ gĩa thakame.\n" +
                "Ya gũtheria nduĩke mwega, Nderikia ho nĩngũthera\n" +
                "Nĩngũgoca Mwathi Jesũ, Nĩatheragia, nĩatheragia\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Rĩu nĩngarũrũkĩte, nĩngũtuĩka mwerũ,\n" +
                "Mũndũ ũria wakwa wa tene nĩagwe thakame-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ndongoragio nĩ ũtheri, ũrĩa wa matu-inĩ,\n" +
                "Ngoro ĩtherete kũna ndĩhumbĩte Kristo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩ wega wa kũgegania Gũikio thakame-inĩ,\n" +
                "Jesũ nowe ũngĩmenya Thakame nĩ yake.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("246. Huuruka Wega");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Huurũka wega, Koma ũguo endete,\n" +
                "Ndahĩtagia, Nĩakũmenyerere wega,\n" +
                "Toro mwega Ndũcoka kuona ũũru\n" +
                "Maithori nĩ makĩ Ngai endete, Koma wega\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Wĩra waku rĩu O na kũgetha gwaku\n" +
                "Nĩwarĩkia ndũcoka kũrĩra rĩngĩ,\n" +
                "Ndũrĩ na thũ Ndũcoka kuona thĩna,\n" +
                "Koma wega ndũcoka kunoga, koma wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Hurũka wega, Rĩu tũtikũgeithia,\n" +
                "Kinya oige, Tũgacemania igũrũ\n" +
                "Tũkũgeithie, na tũtigakua rĩngĩ,\n" +
                "Twacemania Maithori tũtikona, koma wega.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("247. Tubatithie Na Hinya Wa Iguru (NZK 189)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũbatithie na Hinya wa Igũrũ, \n" +
                "ũtwerũhie na wendo waku Jesũ.\n" +
                "\n" +
                "\t\n" +
                "Twagũthaitha, nĩtwakũhoya Jesũ,\n" +
                "ũtũbatithie na Wendo na Roho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\tTũtiagĩrĩire, Tũtirĩ atheru.\n" +
                " Tũthambie tũtherie mehia mathire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nawe Ndũtũra ya matu-inĩ.\n" +
                " Twakũhoya ũũke ũtũrathime.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Tũigue mũgambo, uumĩte igũrũ,\n" +
                "Uuge, “Wĩ mwana wakwa nyeenda muno.”\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("248. Mugate Wa Muoyo (NZK 191)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũgate wa muoyo tũhe rĩu,\n" +
                "ũrĩa waheire andũ aku hwaĩ-inĩ,\n" +
                "Thĩinĩ wa ibuku tũthoomaga,\n" +
                "Tũhe muoyo mwerũ haha rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kiugo kĩrĩa kĩa ma kĩraathime,\n" +
                "Tũigue mũgaambo mũhoro mwega,\n" +
                "Na mĩhĩĩnga yothe ũmĩniine,\n" +
                "Nĩguo tũikarage thĩini waku.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "ũgima na hinya reehe rĩu,\n" +
                "Tũkũrũmĩrĩre hingo ciothe,\n" +
                "Tũtirĩ na hinya o na ũhoti,\n" +
                "We nĩwahotire tũhotithie.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("249. Twatua Kuria Giathi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Twatua kũrĩa gĩathĩ rĩu, Kĩa mũgate ona ndibei,\n" +
                "Tuone thakame ĩgĩitika, Ya mũtharaba ĩtũtue aku,\n" +
                "Twarĩrie ngoro-inĩ ciitũ, Tũkene nganja ithire,\n" +
                "Wĩ hakuhĩ na tũtione, No mũgambo tũkũigua rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tũganĩre wega o rĩu Tũigue ũrĩa woragirũo,\n" +
                "Tũguucie ngoro ici ciitũ, tuone riiri ũcio waku,\n" +
                "Tũkinye o hakuhĩ nawe hau ũroira thakame,\n" +
                "Nĩguo tuone wĩyendi witũ, Twegerekania nawe rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Twatue kuuma metha-inĩ, ĩno ũtwarĩire Jesũ,\n" +
                "Reke tĩigue wega ngoro, Tukũre tũhane tawe,\n" +
                "Twakũrora na twakuona, Tũhananage nawe,\n" +
                "We Mwathani, Mũrutani, Reke tũgwathĩkĩre.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("250. Ihiga Ria Tene Ma (NZK 192)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ihiga rĩa tene ma,nĩndĩhithe harĩ we\n" +
                "Tũma maaĩ na thakame,kuma mbaru-inĩ ciaku\n" +
                "Inine wĩhia wothe,ningĩ na hinya waguo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Mawĩra makwa mothe,matingĩhiingia watho\n" +
                "Kĩyo gĩakwa kĩnene,kĩeha o na maithori\n" +
                "Itingĩnina wĩhia nowe wiki ũngĩniina\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Ndirĩ na ihaki niĩ ,ngwĩhoka o mũtharaba\n" +
                "Ndĩ njaga humba nguo,ndĩ mũhũthũ ndeithagia\n" +
                "Ndĩ o mwĩhia no ngũka ,ũthambie itanakua.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4. \n" +
                "Hingo ya muoyo wakwa,ngũtũra ngwĩhokete\n" +
                "Ndakoma mbĩrĩra-inĩ nĩ njũĩ nĩũkandiũkĩa\n" +
                "Njũke gwaku igũrũ,ndũũre ũnene-inĩ wake.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("251. Ni Mugaambo Wa Muriithi (NZK 193)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Nĩ mũgambo wa Mũrĩithi, ũraiguika kũu werũ-inĩ,\n" +
                "Nake areta ng’ondu ciake, Iria ciũrĩire kũraya.\n" +
                "\n" +
                "\n" +
                "Rehei rehei, rehei ciume mehia-inĩ,\n" +
                "Rehei, rehei, marehei kwĩ Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Nũ ũkwenda gũthiĩ rĩu, Gũteithĩrĩria Mũrĩithi,\n" +
                "Acokie ng’ondu kiugũ-inĩ, Itigakuĩre mehia-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3. \n" +
                "Ndũkaage gũthikĩrĩria, Mwathani ũcio ũrakwĩra.\n" +
                "“Ta thiĩ ũgacarie ng’ondu, O kũndũ guothe kũrĩa irĩ.”\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("252. Twahooya Ngai Umuoe (NZK 194)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Twahooya Ngai, ũmuoe E mũtuungatĩri;\n" +
                "O we warĩkia Kwĩruta, gũtuĩka nduungata.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Twahooya Ngai, ũmuoe E mũtuungatĩri;\n" +
                "Aaheana Kiugo, gĩgatũĩkaga ũtheri.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mũhonokia, nĩtwahooya, nĩguo ũmwandĩke\n" +
                "Ibuku-inĩ rĩaku igũrũ, mũhuunjia wa ũhoro.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩaheo mathaita make, Ma kũhootaga thũ;\n" +
                "Atuĩke njaamba mbaara-inĩ, O kinya agaakua.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Mwathani, ũrĩa ũkũhootana, nĩũndũ wa tha ciaku,\n" +
                "Thũũmbĩ ĩrĩa ya thahabu, nĩyo ũkamũhee.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("253. Magaatura O Na Jesu Kuo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Magaatũũra o na Jesũ kuo, Mũciĩ mwega wa igũrũ,\n" +
                "Makaigua wega ngoro, Arĩa mamwendeete gũũkũ thĩ.\n" +
                "\n" +
                "\n" +
                "Igũrũ kwa Ngai; Magaatũũra Igũrũ;\n" +
                "Igũrũ kwa Ngai; Magaatũũra o na Jesũ kuo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Magaatũũra o na Jesũ kuo, Mũciĩ mwega wa wendani.\n" +
                "Magaatũũra hingo ciothe; Arĩa matheretio gũũkũ thĩ.\n" +
                "\t\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Magaatũũra o na Jesũ kuo, Mũciĩ mwega wa gĩkeno,\n" +
                "Magaatigana na thĩĩna; Maamũhikĩra me gũũkũ thĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Andũ aitũ nĩmũgakinya, Mũciĩ mwega wa igũrũ?\n" +
                "No mwende mũtherio ngoro, Na mũtige ũũrũ gũũkũ thi?\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("254. Ndugetikirie Meehia Makuhoote");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndũgetĩkĩre, Meehia makũhoote,\n" +
                "Wahoota rĩmwe, Nĩũkahoota mothe;\n" +
                "Rũa na ũcaamba, Te merirĩria,\n" +
                "Cũũthĩrĩria Jesũ, Nĩegũgũteithia.\n" +
                "\n" +
                "\n" +
                "Hooya Jesũ agũteithie, Agwĩkĩre hinya ki.\n" +
                "Ekwenda gũgũteithia, Nĩegũgũteithia biũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Eherera andũ ooru, Na ciugo njũru\n" +
                "Tĩĩa rĩĩtwa rĩa Ngai, Ndũkamũrume;\n" +
                "Wĩciiragie wega, Wĩ na ũgima,\n" +
                "Cũũthĩrĩria Jesũ, Nĩegũgũteithia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "O mũhootani, Nĩakaheeo thũũmbĩ,\n" +
                "Notũkahootana, o na twathĩĩnio;\n" +
                "Mũhonokia witũ, twĩkĩre hinya;\n" +
                "Cũũthĩrĩria Jesũ, Nĩegũgũteithia.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("255. Mwathani Niariukite");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "“Mwathani nĩariũkĩte”, Haleluya!\n" +
                "Tũine na araika, Haleluya!\n" +
                "Kũngũĩrai mũno, Haleluya!\n" +
                "Igũrũ na thĩ ciine, Haleluya!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tũkũũrĩtwo nĩ Jesũ, Haleluya!\n" +
                "Mbaara akĩmĩhoota, Haleluya!\n" +
                "Nduma iitũ ĩgĩthira, Haleluya!\n" +
                "Jesũ ndangĩkua rĩĩngĩ, Haleluya!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ihiga rĩa mbĩĩrĩra, Haleluya!\n" +
                "Riahiingite o tũhũ, Haleluya!\n" +
                "Jesũ nĩariũkire, Haleluya!\n" +
                "Kristo e Paradiso; Haleluya!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Mũthamaki e muoyo Haleluya!\n" +
                "Ruo rũa gĩkuũ rwĩ ha? Haleluya!\n" +
                "Akuire tũhonoke, Haleluya!\n" +
                "Gĩkuũ nĩgĩatooririo Haleluya!\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("256. Nindithaambire Mooko (NZK 198)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩndĩthaambire mooko rũcii-inĩ ndookĩra,\n" +
                "Ndutĩire Jesũ wĩra mũthenya wothe biũ.\n" +
                "\n" +
                "\n" +
                "Rora wega ma njĩra-inĩ guothe,\n" +
                "ũrutĩre Jesũ wĩra mwega ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ndegaga matũ makwa menyage mahiinda,\n" +
                "Mooko marute wĩra wa tha hingo ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Maitho nĩmakũrora mooko wĩra-inĩ,\n" +
                "Matuungatĩre Jesũ hatarĩ ũgwati.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("257. Iguai Mugaambo Mbeeca Ikigaamba");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Igua Mũgambo, Mbeca ikĩgamba.\n" +
                "Ciothe nĩ cia Jesũ, Nĩaciamũkĩre.\n" +
                "\n" +
                "\n" +
                "Nĩiragamba Nĩiragamba, Ciigue ikĩgamba,\n" +
                "Ciothe nĩ cia Jesũ, Nĩaciamũkĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2. \n" +
                "Nĩũgambe o kũgamba, ciume moko-inĩ,\n" +
                "Nĩ Jesũ tũrahe, Tondũ tũrĩ ake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Mũgambo ũcio wacio nĩguo kĩheo\n" +
                "Kĩrĩa tureheire Jesũ mũtwendi.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("258. Wendo Niguo Gikeno (NZK 199)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Wendo nĩgũo gĩkeno, wagagĩria njĩra\n" +
                "ũtũteithagia gũtuga andũ hingo ciothe\n" +
                "\n" +
                "\n" +
                "Ngai nĩ wendo twĩ ciana ciake,\n" +
                "Nĩ wendo, tũtuĩke take,\n" +
                "Wendo nĩguo gĩkeno, wagagĩria njĩra,\n" +
                "ũtũteithagia gũtuga andũ hingo ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Thĩ ĩiyũirwo nĩ kĩeha, mĩrĩmũ na gĩkuũ,\n" +
                "Twĩ na wendo nĩtũgerie kũguucia andũ arĩa angĩ.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Muoyo ũyũ wathira, twambatio matuinĩ,\n" +
                "Tũkaina mĩndĩ na mĩndĩ, wendo wake Jesũ\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("259. Jesu Niwe Munyeendi (NZK 197)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩwe mũnyendi,na nĩnjũĩ nĩguo ki!\n" +
                "Mbuku ya Ngai yugaga,nĩwe mwendi wa twana.\n" +
                "\n" +
                "\n" +
                "ĩĩ nĩanyendete ,Jesũ we mwene;\n" +
                "ĩĩ nĩanyendete,nĩnjuĩ nĩguo ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Jesũ nĩwe mũnyeendi ,nĩanguĩrĩire mũtĩ-inĩ\n" +
                "Wĩhia wakwa anine,Mwana wake ndakore.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Jesũ nĩwe mũnyeendi ,rĩrĩa ndĩhatĩka-inĩ\n" +
                "Ekũu matu-inĩ no anjũthagĩrĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Jesũ nĩwe mũnyeendi ,tũtigatigana ni;\n" +
                "Tũrĩthiaga nake,O kinya mũciĩ gwake.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("260. Riria Jesu Agacooka (NZK 196)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Rĩrĩa Jesũ agacoka Aiyĩre managĩ\n" +
                "Na managĩ nĩmo andũ arĩa anakũũra\n" +
                "\n" +
                "\n" +
                "Magathera ta ngata, ota ya kĩwariĩ\n" +
                "Noguo magathakara managĩ make.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Akongania akongania, amatware gwake;\n" +
                "Arĩa ega na atheru arĩa anakũũra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Twana tũtũ,twana tũtũ mwendete mũkũũri\n" +
                "Nĩ inyuĩ mwĩ managĩ,managĩ make.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("261. Gwitu Twaikara Na Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Gwitũ twaikara na Jesũ nĩtũkenaga\n" +
                "Nĩtũkenaga nĩtũkenaga\n" +
                "Gwitũ twaikara na Jesũ nĩtũkenaga\n" +
                "Gĩkeno kĩingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Rĩrĩa Baba ena Jesũ nĩtũkenaga \n" +
                "Nĩtũkenaga nĩtũkenaga\n" +
                "Rĩrĩa baba ena Jesũ nĩtũkenaga\n" +
                "Gĩkeno kĩingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Rĩrĩa Maitũ ena Jesũ nĩtũkenaga \n" +
                "Nĩtũkenaga nĩtũkenaga\n" +
                "Rĩrĩa maitũ ena Jesũ nĩtũkenaga\n" +
                "Gĩkeno kĩingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Mwarĩ wa Maitu e na Jesũ nĩtũkenaga,\n" +
                "Nitũkenaga, nitũkenaga,\n" +
                "Mwarĩ wa Maitũ e na Jesũ, nitũkenaga,\n" +
                "Gĩkeno kĩingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\n" +
                "Mũrũ wa Maitũ e na Jesu, nĩtũkenaga,\n" +
                "Nĩtũkenaga, nĩtũkenaga,\n" +
                "Mũrũ wa Maitũ e na Jesũ, Nĩtũkenaga,\n" +
                "Gĩkeno kĩingĩ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("262. Ngai Niendeete Tunyoni (NZK 201)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngai nĩendeete tũnyoni twĩ gũũkũ thĩ,\n" +
                "ũrĩa endeete tũnyoni, noguo niĩ anyendeete.\n" +
                "\n" +
                "\n" +
                "Nĩanyendeete, nĩanyendeete, nĩanyendeete o na niĩ,\n" +
                "Nĩ njũũĩ nĩanyendeete o na ndĩ mũnyinyi.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Athakaragia mahũa na raangi ĩrĩa njega;\n" +
                "ũrĩa endeete mahũa, noguo anyendeete niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ngai ũrĩa wombire nyoni hamwe na mahũa,\n" +
                "Ndariganĩirũo nĩ ciana, nĩ ma nĩaciendeete.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("263. Ninjiguaga Gikeno Kiingi (NZK 200)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩnjiguaga gĩkeno kĩingĩ,\n" +
                "Tondũ wa ndeto njĩĩrĩtwo nĩ Ngai,\n" +
                "Jesũ nĩoimire gwake igũrũ,\n" +
                "Anjĩĩre Ngai nĩanyendeete ma.\n" +
                "\n" +
                "\n" +
                "Nĩanyendeete Mwathani Jesũ,\n" +
                "Nĩanyendeete, Nĩanyendeete,\n" +
                "Nĩanyendeete, Mwathani Jesũ,\n" +
                "Jesũ nĩanyendeete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na rĩrĩa ingĩmũtiganĩria,\n" +
                "We akiragĩrĩria, kũnyeenda:\n" +
                "Nakũnjookia kũrĩ we ngoro-inĩ,\n" +
                "Na nĩ ma Jesũ no anyendeete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Rĩrĩa ngakinya igũrũ kwa Ngai,\n" +
                "Nĩngahotaga kũina atĩrĩ, \n" +
                "O na ndamenaga Jesũ mũno.\n" +
                "Niĩ mũndũ mũũru no anyendeete.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("264. Ni Guuka Eguuka");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩgũũka egũũka Jesũ witũ, Atũtware mũciĩ,\n" +
                "Rĩu ngwĩhaarĩria atanooka, Gũtũtwara mũciĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Jesũ egũũka na araika, Atũtware mũciĩ,\n" +
                "Rĩu ngwĩhaarĩria atanooka, Gũtũtwara mũciĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Jesũ ekũiyĩra andũ eega, Amatware mũciĩ,\n" +
                "Rĩu ngwĩhaarĩria atanooka, Gũtũtwara mũciĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Jesũ egũũka akinyĩte itu, Atũtware mũciĩ,\n" +
                "Rĩu ngwĩhaarĩria atanooka, Gũtũtwara mũciĩ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("265. Ningeekira Thuumbi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩngekĩra thũmbĩ igũrũ kwa Baba,igũrũ kwa Baba,igũrũ kwa Baba\n" +
                "Nĩngekĩra thũmbĩ igũrũ kwa Baba, gwĩ gĩkeno gĩkeno kĩingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Ngahũra kĩnanda igũrũ kwa Baba, igũrũ kwa Baba, igũrũ kwa Baba\n" +
                "Ngahũra kĩnanada igũrũ kwa Baba, gwĩ gĩkeno kĩingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Nĩngekĩra kanjũ,igũrũ kwa Baba,igũrũ kwa Baba,igũrũ kwa Baba\n" +
                "Nĩngekĩra kanjũ igũrũ kwa Baba gwĩ gĩkeno kĩingĩ\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("266. Kaana Ka Mariamu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kaana ka Mariamu nĩ kega mũno,\n" +
                "Kaigĩtwo nĩ nyina, kiugũ-inĩ, he ng’ombe, ũtukũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Arĩithi makĩigua araika aingĩ,\n" +
                "Makĩinĩra Jesũ, kiugũ-inĩ, he ng’ombe, ũtukũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Andũ ogĩ a njata maaturĩtie ndu,\n" +
                "Magĩkĩona kaana. Kiugu-inĩ, he ng’ombe, ũtukũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Twana tũũtũ na inyuĩ, Mwendage Jesũ,\n" +
                "ũcio waciarĩirũo kiugũ-inĩ, he ng’ombe, ũtukũ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("267. Njata Ngeengi Cia Na Kuu Matu-ini");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Njata ngeengi cia na kũu matu-inĩ,\n" +
                "Ndiũĩ kana hihi mwaririkana,\n" +
                "O ũrĩa njata ta inyũĩ yatwariire,\n" +
                "Andũ oogĩ a njata O Bethilehemu?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩmuonire iheeo cia goro mũno?\n" +
                "ĩĩ rĩrĩa maacaragĩrĩria kiugũ?\n" +
                "Nĩmuonire rĩrĩa maaturĩtie ndu,\n" +
                "He gakenge keega mũharatĩ-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩmuonire atumia makĩrĩra,\n" +
                "Rĩrĩa Herode aamoragĩire twaana?\n" +
                "Nĩmuonire Jusufu erũo nĩ Ngai,\n" +
                "Mũmuonie ũtheri orĩre Misiri?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩmuonire Mũhonokia agĩthĩĩna?\n" +
                "Muoyo wake aarutĩire andũ eehia.\n" +
                "Njata ngeengi cia na kũu Matu-inĩ,\n" +
                "Korũo ũguo muonire na niĩ ndamenya.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("268. Tuoko Ni Twa Wira");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tuoko nĩ twa wĩra wa Jesũ,\n" +
                "Karũrĩmĩ kamũinagĩre,\n" +
                "Tũtũ tũtaaragwo nĩwe,\n" +
                "Kamũgaambo kainage rũĩmbo.\n" +
                "\n" +
                "\n" +
                "Tuoka gwaku Jesũ Riu tũrĩ twana rũciinĩ,\n" +
                "Jesũ nĩtuoka rĩu, Tũkũmenye ki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Na tũgũrũ twĩrĩ nĩ twa gũthiĩ,\n" +
                "Twerekere o matũ-inĩ,\n" +
                "Tuitho tũthoomage Ibuku,\n" +
                "Rĩonanĩtie wendo wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Na ndĩ na gakoro ka Jesũ,\n" +
                "Ndĩ kamũndũ ga kũhonokio,\n" +
                "Ndĩmũtuungate ndĩ kaana,\n" +
                "Aahonokie ndĩ mũnyiinyi.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("269. Jesu Nienda Twaana Tuniini Ta Nii");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩenda twana tũniini ta niĩ,\n" +
                "Jesũ nĩenda twana tũniini ta niĩ,\n" +
                "Tũnini ta niĩ, tũthii harĩ we;\n" +
                "Jesũ nĩenda twaana tũnini niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Jesũ nĩendeete andũ anene ta we,\n" +
                "Jesũ nĩendeete andũ anene ta we,\n" +
                "Anene ta we, mathiĩ harĩ we,\n" +
                "Jesũ nĩendeete andũ anene ta we.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("270. Mbuku Ikuuga");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mbuku ĩkuuga Ngai nĩ wendo\n" +
                "Mbuku ĩkuuga nagai nĩ wendo.\n" +
                "\n" +
                " \t\t\t\t\t\t\t\t\t\t2.\n" +
                "Mbuku ĩkuuga tũtuge andũ\n" +
                "Mbuku ĩkuuga tũtuge andũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Mbuku ĩkuuga tũheanage\n" +
                "Mbuku ĩkuuga tũheanage.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("271. Jesu Nienda Twana Tuothe");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩenda twana tuothe nĩatwendete na ngoro\n" +
                "Twana twa kũndũ guothe no ta tũmwe kwĩ Jesũ\n" +
                "Jesũ nĩenda twana tuothe tũrĩ thĩĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Jesũ nĩenda twana tuothe nĩenda gũtũrathima \n" +
                "Natuo tũngĩmwĩtĩkia we no gũtũiguĩra tha \n" +
                "Jesũ nĩenda twana tuothe tũrĩ thĩĩ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Jesũ nĩenda twana tuothe nĩagacoka gũkũ thĩĩ\n" +
                "Hĩndĩ ĩyo nĩagatũhe handũ hega igũrũ\n" +
                "Jesũ nĩenda twana tuothe tũrĩ thĩĩ\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("272. Zakayo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Zakayo arĩ mũkuhĩ ma,mũndũ mũkuhĩ\n" +
                "Nĩahaicire mũtĩ nĩguo one,Mũhonokia\n" +
                "Nake Jesũ akinya ho,akĩrora mũtĩ.\n" +
                "\n" +
                "\n" +
                "{uga kuuga ũtekũina}\n" +
                "“Akiuga Zakayo uma mũti igũrũ”\n" +
                "Nĩtũgũthiĩ nawe ogwaku\n" +
                "Nĩtũgũthiĩ nawe ogwaku\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("273. Ninyeenda Muno Thiage Sabbath School");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩnyenda mũno thiage Sabbath School, Sabbath School,\n" +
                "Nĩnyenda mũno thiage, Thabatũ rũciinĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nĩnyenda mũno nyinage ũhoro wa Jesũ,\n" +
                "Nĩnyenda mũno nyinage, Thabatũ rũciinĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩnyenda mũno ndutagĩre Jesũ mũhothi,\n" +
                "Nĩnyenda mũno ndutage, Thabatũ rũciinĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩnyenda mũno hoyage o Jesũ, O Jesũ,\n" +
                "Nĩnyenda mũno hoyage, Thabatũ rũciinĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Nĩnyenda mũno ndutagwo ũhoro wa Jesũ,\n" +
                "Nĩnyenda mũno ndutagwo, Thabatũ rũciinĩ.\n" +
                "\n" +
                "6.\t\n" +
                "Nĩnyeenda mũno njugage uhoro wa mũtwe,\n" +
                "Nĩnyeenda mũno njugage, Thabatũ rũciinĩ.\n" +
                "\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("274. Ndi Na Bibilia Igiri");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndĩ na Bibilia igĩrĩ,\n" +
                "We ndũrĩ nayo na ti wega;\n" +
                "Nĩĩngũkũhe yakwa nĩ wendo,\n" +
                "Rĩu wĩ na Bibilia njega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Niĩ ndĩ na mbica igĩrĩ,\n" +
                "We ndũrĩ nayo na ti wega;\n" +
                "Nĩĩngũkũhe yakwa nĩ wendo,\n" +
                "Rĩu wĩ na mbica njega ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Niĩ ndĩ na mathoomo meerĩ,\n" +
                "We ndũrĩ narĩo na ti wega,\n" +
                "Nĩĩngũkũhe rĩakwa nĩ wendo,\n" +
                "Rĩu nawe wĩ na rĩega ma.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("275. Miithi Itandatu Ni Wira");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mĩĩthĩ ĩtaandatũ nĩ wĩra, Wa mũgwanja nĩ wa Jesũ,\n" +
                "ũhuurũkwo nĩ Mũtheru, Tondũ ũcio nĩ wa Jesũ.\n" +
                "\n" +
                "\n" +
                "ũmwe ĩĩrĩ, ĩtatũ, ĩna, ĩtano, ĩtandatũ,\n" +
                "ĩtandatũ nĩyo iitũ, Wa mũgwanja nĩ wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Atwĩraga tũkahooe, Mũthenya ũcio wake,\n" +
                "Okire thĩ gũtũruta, Tũmũrũmagĩrĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nĩtũthiĩ nyumba-inĩ yake, Tũrutwo ciugo ciake,\n" +
                "Rĩathani rĩake tũrũmie, Nĩguo tũtũũre nake.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("276. Jesu Muriithi Wakwa");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩ Mũrĩithi wakwa, Ndĩ kagoondu kanini;\n" +
                "ũnjikarie ũtukũ, nginye wega rũciinĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "ũmũthĩ nĩũndeithĩtie; Nĩngũkwĩra nĩ wega,\n" +
                "Handũ hothe nĩũnyonetie, Na ũkandĩithia ta ng’ondu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "ũ’thengerie ũũru wothe, Nĩguo ũ thaambie ngoro;\n" +
                "Na ũteithie arĩa angĩ, Othe tũtuĩke aku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nĩguo rĩrĩa ngahuuruka, nyume gũũkũ thĩ ĩno,\n" +
                "Ngatũũrania hamwe nawe, Gwaku tene na tene.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("277. Ngwenda Ngoruo Ndihaariirie");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngwenda ngorũo ndĩhaarĩirie, Mwathani,\n" +
                "Ngwenda ngorũo ndĩharĩirie Mwathani,\n" +
                "Ikeno cia thĩ no cia tũhũ,\n" +
                "Kĩngitĩre kinya ũgooka!\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("278. Jesu Mwendwa Muhonokia (NZK 210)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ Mwendwa Mũhonokia, gũtirĩ ũngĩ nowe,\n" +
                "Jesũ Mwendwa Mũhonokia Nowe wiki njũthĩrĩirie,\n" +
                "Jesũ Mwendwa Mũhonokia rĩu tene na tene\n" +
                "Ndĩragũcaria Wee ũrĩ ũtoonga wakwa\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("279. Mwathani Nyita Riu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mwathani, Nyita rĩu, Ndoongoria, ũndũũgamie,\n" +
                "Ndĩ mũnogu Ndirĩ, na hinya,\n" +
                "Gwĩ kĩhuuhũkanio, gwatuka ũndoongorie,\n" +
                "Mwathani, nyita guoko, nyinũkia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ingĩaga gĩkeno, Mwathani nguhĩrĩria,\n" +
                "Muoyo wakwa o na wathira,\n" +
                "Ndaakaya, ndeetana, nyita guoko ndikaagwe,\n" +
                "Mwathani nyita guoko, nyinũkia.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("280. Kristo Nowe");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Kristo nowe, Kristo nowe,\n" +
                "Rĩrĩa ingĩona mathĩĩna, Ndakandekia,\n" +
                "Kristo nowe, Kristo nowe,\n" +
                "Ngoro yakwa ĩrorete; Kristo we wiki.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("281. Witikio Wakwa Ngugaira Aria Angi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Wĩtĩkio wakwa ngũgaira arĩa angĩ,\n" +
                "Wĩtĩkio wakwa nĩngũgaya narua;\n" +
                "Rĩrĩa Jesũ angĩnjĩta, no ndĩmwathĩkĩre,\n" +
                "Na wĩhokeku na mĩtũkĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("282. Ndi Njira-ini Ngithii Matu-ini");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ndĩ njĩra-inĩ ngĩthiĩ matu-inĩ,\n" +
                "Nyinũke kwĩ Mũhonokia,\n" +
                "Magerio mothe no magaathira,\n" +
                "Ndĩ Njira-inĩ nyinũke.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("283.  Twagucookeria Ngaatho");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Twagũcookeria Ngaatho We Ngai,\n" +
                "Tondũ wa Iraathimo cia matu-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tondũ wa nyũũmba hamwe na nguo,\n" +
                "Na hinya na ũmenyereri waku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tondũ wa wendo wa aciari aitũ,\n" +
                "Nĩtwakũgaatha Baba wa Igũrũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("284. Ngoma Airagia Ngoro Yaakwa");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngoma airagia ngoro yakwa,\n" +
                "Jesũ atonya agatheria,\n" +
                "Kũrĩ therunji ĩĩ, kũrĩ therunji, ĩĩ\n" +
                "shaitani angeria ndiugaga Aca!\n" +
                "ngoma airagia ngoro yakwa,\n" +
                "Jesũ agatheria ta ira,\n" +
                "shaitani ndooĩ no Jesũ wĩ ngoro-inĩ,\n" +
                "Nake anjerũhagie.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("285. Ngai Akuragia Nyeni Gwiitu Muguunda");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngai akũragia nyeni gwitũ, Mũgũũnda,\n" +
                "Ngai akũragia irio gwitũ, Tũmwende.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Ngai akũragia mbembe gwitũ, Mũgũũnda,\n" +
                "Ngai akũragia irio gwitũ, Tũmwende.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Ngai akũragia waru gwitũ, Mũgũũnda,\n" +
                "Ngai akũragia irio gwitũ, Tũmwende.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Ngai akũragia mũhĩa gwitũ, Mũgũũnda,\n" +
                "Ngai akũragia irio gwitũ, Tũmwende.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Ngai akũragia kĩgwa gwitũ, Mũgũũnda,\n" +
                "Ngai akũragia irio gwitũ, Tũmwende.\n" +
                "\n" +
                "6.\t\n" +
                "Ngai akũragia tũmahũa, gwitũ Mũgũũnda,\n" +
                "Ngai akũragia tũmahũa, gwitũ Tũmwende.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("286. Mukuunga Mbura Ni Wa-u?");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1. \n" +
                "Mũkũnga mbura nĩ wa-ũ?  Nĩnjũĩ, nĩnjũĩ\n" +
                "Mũkũnga mbura nĩ wa Ngai, Nĩkĩo ndĩmwendete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                " Mũkũnga mbura ũrehagwo, Nĩ Ngai, Nĩ mwega,\n" +
                "Mũkũnga mbura nĩ rũũri, Atĩ e hamwe na ithuĩ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("287. Nuu Ungiumba Ihua?");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nũũ ũngĩũmba ihũa, Ndingĩhota ĩĩ-wee\n" +
                "Nũũ ũngĩũmba ihũa, Gũtirĩ thengia Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nũũ ũngĩkiuria mbura, ndingĩhota ĩĩ-wee?\n" +
                "Nũũ ũngĩkiuria mbura, Gũtirĩ thengia Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nũũ ũngĩkĩaria riũa, Ndingĩhota ĩĩ-wee?\n" +
                "Nũũ ũngĩkĩaria riũa, Gũtirĩ thengia Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nũũ ũngĩreehe rũhuuho, Ndingĩhota –wee?\n" +
                "Nũũ ũngĩreehe rũhuuho, Gũtirĩ thengia Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Nũũ ũngĩtheria ngoro, Ndingĩhota ĩĩ-we?\n" +
                "Nũũ ũngĩtheria ngoro, gũtĩrĩ thengia Ngai.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("288. Tubeca Tuutu Muhiuhe");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũbeeca tũũtũ mũũhiũhe, Mũkinyie ũhoro mwega,\n" +
                "Mwĩre twaana twa kũraaya, Jesũ nĩatwendeete.\n" +
                "\n" +
                "\n" +
                "Tũbeeca tũũtũ mũhiũhe,\n" +
                "Mwĩre twana twa kũraaya,\n" +
                "Atĩ “Jesũ nowe wa ma”\n" +
                "Nĩatwendeete tuothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tũbeeca tũũtũ mũhiũhe, Muonanie ũrĩa tũiguĩte,\n" +
                "Mũteithie twaana kũmenya, ũrĩa tũrutagwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũbeeca tũũtũ mũhiuhe, Na nĩmũkuongerereka,\n" +
                "Ambĩrĩriai rũgeendo, Mwatũmwo na wendo.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("289. Andu A Thi Mabataire Kuona Mwathani (NZK 215)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Andũ a thĩ mabataire kuona Mwathani;\n" +
                "Andũ a thĩ mabataire kuona Mwathani;\n" +
                "Nĩarehage gĩkeno na ndũhiũ nyingĩ mũno,\n" +
                "Andũ a thĩ mabataire kuona Mwathani.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("290. Guuku Ni Kuri Waganu Muingi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Gũũkũ nĩ kũrĩ waganu mũingĩ;\n" +
                "Nĩtũhooreragio nĩ Mũhonokia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Gũũkũ nĩ kũrĩ Mĩhang’o Mĩingĩ\n" +
                "Kaba tũrũmagĩrĩre Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Gũũkũ nĩ kũrĩ kĩrĩro kĩingĩ;\n" +
                "Jesũ nĩwe ũtũũmagĩrĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Tũtiũĩ ũgwati wa thuutha;\n" +
                "Nĩtũũĩ Jesũ nĩ Mũhonokio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Jesũ nĩwe watooririe gĩkuũ;\n" +
                "Mũthenya ũcio aariũkire.\n" +
                "\n" +
                "6.\t\n" +
                "Twaniina wĩra witũ wa gũũkũ;\n" +
                "Nĩtũgathiĩ kwa Ngai Igũrũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("291. Roho Wa Ngai Witu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Roho wa Ngai witũ ũka harĩ niĩ.\n" +
                "Roho wa Ngai witũ, ũka harĩ niĩ.\n" +
                "Njororoiya thoondeka wega,\n" +
                "Roho wa Ngai witũ, ũka harĩ niĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("292. Twaana Tuothe Tumenye");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Twana tuothe tũmenye\n" +
                "Twana tuothe tũmenye\n" +
                "Twana tuothe tũmenye Jesũ nũũ\n" +
                "Nĩ ihũa rĩa mũkuru\n" +
                "Nĩ njata ya rũcini\n" +
                "Nĩmwega wa ngiri ikũmi\n" +
                "ĩrai twana tuothe.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("293. Jesu Niendaga Nduike");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ nĩendaga nduĩke, ta mĩrũri ya riũa;\n" +
                "Ndĩmũtheragĩre wega, Nĩguo akenage.\n" +
                "\n" +
                "\n" +
                "Mĩrũri, Mĩrũri,\n" +
                "Jesũ nĩendaga mĩrũri;\n" +
                "Mĩrũri, Mĩrũri\n" +
                "Tũrĩ mĩrũri yake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Jesũ nĩendaga ndũĩke, Wa kwendana na andũ;\n" +
                "Ndĩmoonagie wega mũno, ũrĩa angenagia.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("294. Jesu Ngukuuma Thuutha");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Jesũ ngũkuuma thuutha, Tondũ nĩũranjĩta,\n" +
                "Ngũũka ngwĩhokete ma, ũnginyie matu-inĩ.\n" +
                "\n" +
                "\n" +
                "Nĩngũũka Jesũ, Nĩngũũka Jesũ,\n" +
                "Ngũrũmĩrĩre o ro handũ hothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tũitho no twage kuona, Na tũgũrũ no tũũre,\n" +
                "O na hinya no njage, No we wĩ na hinya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Kĩeha na thĩĩna nĩ thũ, meehia maahinga njĩra,\n" +
                "Ndĩiyuragwo nĩ hinya, Ndakũrũmĩrĩra.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("295. Nyina Nyau Oigaga");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nyina nyau oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina nyau oigaga Nĩanyũmbĩire, Wa gatandatũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Nyina ngui oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina ngui oigaga Nĩanyũmbĩire, Wa gatandatũ.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Nyina ngũkũ oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina ngũkũ oigaga Nĩanyũmbĩire, Mũthi wetano.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Nyina mbata oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina mbata oigaga Nĩanyũmbĩire, Mũthi wetano.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Nyina nyoni oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina nyoni oigaga Nĩanyũmbĩire, Mũthi wetano.\n" +
                "\n" +
                "\n" +
                "6.\t\n" +
                "Nyina ng’ombe oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina ng’ombe oigaga Nĩanyũmbĩire, Wa gatandatũ.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("296. Twi Gakundi Ga Twaana\n");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Twĩ gakuundi ga twaana, Tũnini tũhe hinya,\n" +
                "No tũkwenda tũrute, Wĩra mwega wa Jesũ.\n" +
                "\n" +
                "\n" +
                "Tũreketio twĩ gakuundi, Na nĩ ka Jesũ\n" +
                "Na nĩ ka Jesũ, Tũreketio twĩ gakuundi,\n" +
                "Twĩkĩre kĩyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Kũrĩ twaana twa kũũngĩ, Twĩ mũrĩmo wa iria,\n" +
                "Tũhooi mĩhianano, nĩ tondũ tũtiũĩ Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Mũrutani atũmwo atũrute kũhooya,\n" +
                "Jesũ wiki nĩ geetha, Tũthaambio meehia mathire.\n" +
                "\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("297. Tigwo Uhoro");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tigwo ũhoro, Ngai e nawe,\n" +
                "Tigwo ũhoro, Na tha ciake,\n" +
                "Tigwo tũkũhooyagĩre,\n" +
                "Ngai nĩakũraathime.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("298. Tuitho Tutu Thuuragai");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Tũitho tũũtũ thuuragai wa kuona, Tũitho tũũtũ thuuragai wa kuona,\n" +
                "Mũhonokia e igũrũ, Arorete na wendo,\n" +
                "Tũitho tũũtũ thuuragai wa kuona.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Tũtũ tũũtũ thuuragai cia kũigua, Tũtũ tũũtũ thuuragai cia kũigua,\n" +
                "Jesũ akua atwĩrire, ũkai kũrĩ niĩ, Tũtũ tũũtũ thuuragai cia kũigua.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Tũmĩromo thuuragai cia kwaria, Tũmĩromo thuuragai cia kwaria,\n" +
                "Tũine, Tũkĩgocaga, Tũhoe na tũkene, Tũmĩromo thuuragai cia kwaria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\t\n" +
                "Tuoko tũũtũ thuragai wa gwĩka, Tuoko tũũtũ thuragai wa gwĩka,\n" +
                "Jesũ nĩatũrutĩire wĩra nĩtũkĩmũigue, tuoko tũtũ thuragai wa gwĩka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\t\n" +
                "Tũgũrũ thuragai gwa gũthiĩ, Tũgũrũ thuragai gwa gũthiĩ,\n" +
                "Gerai njĩra njeke, gwakia nginya gũtuke, Tũgũrũ thuragai gwa gũthiĩ.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        song = new Song();
        song.setTitle("299. Muriithi Mwega W Ngondu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Mũrĩithi mwega wa ng’ondu cia Isiraeli,\n" +
                "Amũkĩra ng’ondu ciaku, Na ũcihĩmbĩrie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\t\n" +
                "Rekei twana o natuo Tũũke tũrathimwo\n" +
                "Nĩkĩo mwathi wa araika Okire gũkũ thĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\t\n" +
                "Twakũrehera na ngatho ũciari ũyũ witũ,\n" +
                "Nĩtwakũne amũkĩra. Tondũ tũrĩ aku.\n");
        song.setPhoto(R.drawable.img5);
        songs.add(song);

        return songs;
    }
}
