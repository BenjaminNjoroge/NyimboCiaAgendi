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
import net.webnetworksolutions.nyimbociaagendi.activity.MainActivity;
import net.webnetworksolutions.nyimbociaagendi.adapter.SongBookAdapter;
import net.webnetworksolutions.nyimbociaagendi.other.DividerItemDecoration;
import net.webnetworksolutions.nyimbociaagendi.pojo.Song;

import java.util.ArrayList;


public class OldSongsFragment extends Fragment {

    private SearchView sv;

    private View rootView;
    private ImageView imgHeader;

    public static final String TAG = OldSongsFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_COLOR = "color";

    // TODO: Rename and change types of parameters
    private int color;

    private RecyclerView recyclerView;


    public OldSongsFragment() {
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
      rootView = inflater.inflate(R.layout.fragment_old_songs, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

        imgHeader = (ImageView)rootView.findViewById(R.id.backdrop);



        sv=(SearchView)rootView.findViewById(R.id.mSearch);

        //recyclerview and properties
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_square_recycler);
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
                (CollapsingToolbarLayout)rootView. findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle("Ibuku Rikuru");
        AppBarLayout appBarLayout = (AppBarLayout)rootView. findViewById(R.id.appbar);
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



    private ArrayList<Song> getSongs(){
        ArrayList<Song>songs=new ArrayList<>();
        Song song=new Song();
        song.setTitle("1.Ninguinira Ngai Hoti (NZK 38)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Nĩngũinĩra Ngai hoti ya maũndũ mothe,\n" +
                "Ombire irĩma na iria o na matu mothe,\n" +
                "ũũgĩ wake watũmire riũa rĩthamake,\n" +
                "Mweri wathage ũtukũ na njata ikamũigua\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "Wega wa Mwathani witũ ũtũheaga irio,\n" +
                "Arĩkia kũũmba indo ciothe oigire-nĩ njega,\n" +
                "Kũria guothe ndaikia maitho no ũrirũ waku,\n" +
                "Tĩĩri ũyũ o na matu nĩ cia kũgegania.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "Mĩtĩ yothe na mahũa ii na riiri waku,\n" +
                "Matu mathiũrũrũkaga o rĩrĩa ũngĩenda,\n" +
                "Ciũmbe ciothe ii na muoyo uumĩte harĩ we,\n" +
                "Kũrĩa guothe tũngĩũrĩra no ũgakorũo kuo.\n");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("2.He Bibilia Njata Ya Gikeno");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "He Bibilia, njata ya gĩkeno, Nĩguo agendi mone ũtheri,\n" +
                "Thayũ rĩu ndũngĩkĩgirĩrĩka, tondũ Jesũ nĩatũhonokirie.\n" +
                "\n" +
                "He Bibilia nĩcio ciugo theru, \n" +
                "ũtheri ũcio nĩũkũndongoria,\n" +
                "Watho kĩrĩkanĩro ona wendo, \n" +
                "Irũmanĩrĩre nginya tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                "He Bibilia rĩrĩa ndĩ na kĩeha, ndataangwo nĩ meehia o na guoya,\n" +
                "ũhe ciugo icio ciaririo nĩ Jesũ, Ndĩmuonage e hakuhĩ na niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                "He Biblia nĩguo hote kuona, mogwati maria me gũũkũ thĩ ĩno,\n" +
                "ũtheri ũcio wa Mwathani Jesũ, ũtuonagia wega njĩra ya ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "He Bibilia ũtheri wa muoyo, Na nĩguo wacoka ũkandiũkia,\n" +
                "Na ũnyonie ũtheri wa matuinĩ, Na nyone ũkengi wa Mwathani.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("3.Mbuku Ya Ngai");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMbuku ya Ngai ĩtwĩra atĩa?\n" +
                "Wĩyũrie naniĩ ndĩyũrie;\n" +
                "Tũhĩtithĩtio nĩ tũnua twa andũ \n" +
                "Mbuku theru yugete atĩa?\n" +
                "\n" +
                "Mbuku ya Ngai yugĩte atĩa?\n" +
                "Mbuku ya Ngai mĩthomage;\n" +
                "Igua maathani,mathaani ikũmi\n" +
                "Weterere Jesũ Kristo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n No andũ  anyinyi marũmbũyagia\n" +
                "Maathani macio ikũmi;\n" +
                "Rĩa kana nĩ ite ona rĩa kerĩ\n" +
                "Makagayania rĩa ikũmi\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNiatĩa mũkoiga mwathani acoka \n" +
                "Inyui mũgarũranagia;\n" +
                "Mũkamwĩra atĩa mũtigakane\n" +
                "Mwĩcũraniei o rĩu.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("4.Meha Aria Mamenyagirira");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMeha arĩa mamenyagĩrĩra Maathani ma Ngai wa Igũrũ,\n" +
                "O hamwe na wĩtĩkio wa Jesũ, Acio nĩo andũ aria atheru\n" +
                "\n" +
                "Watho wa Ngai, watho wa Ngai,\n" +
                "Watho wa Ngai, watho wa Ngai, Na wĩtĩkio wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nIthuĩ nĩ ithuĩ tũmenyagĩrĩra, Maathani ma Ngai wa Igũrũ,\n" +
                "O hamwe na wĩtĩkio wa Jesũ, Gĩkeno nĩ kia andũ aria atheru.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nGũkena nĩ arĩa marĩkuaga, thutha ũyũ me thĩinĩ wa Jesũ,\n" +
                "Mahurukage wĩra mũritũ, wĩra ũcio wao ndũkora.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nMenyerera thũmbĩ yaku wega, Mũndũ ndakae gũthiĩ nayo,\n" +
                "Mwathi Jesũ e hakuhĩ gũũka, Tũrĩhwo mũndũ wĩra wake.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("5.Igua Wega Ciugo Cia Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nIgua wega ciugo cia Jesu, Cia muoyo na ningĩ cia ma,\n" +
                "Andu atheru uririkane, Mendete gwathĩkĩra watho.\n" +
                "\n" +
                "Gwathĩkĩra watho ucio wake, Gwathĩkĩra watho wa Jesu,\n" +
                "Gukena nĩ arĩa mamuiguaga, Acio nĩo makenaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nKuigua gwiki, o na gwĩtĩkia, Gwĩtĩkĩra watho wake,\n" +
                "Itingĩhota gutuhonokia, Thengia o gwathĩkĩra watho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nAcio mena rutha matonye, muciĩ wao na gĩkeno,\n" +
                "Na matingĩĩhia matirĩ wĩhia, makaheyo mutĩ wa muoyo.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("6.Ndongoria Riu Jehova (NZK 156)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNdongoria rĩu Jehova, Ndĩ mũgendi gũkũ thĩ,\n" +
                "Ndirĩ hinya we wĩ naguo, Nyita na guoko gwaku\n" +
                "We mũgate wa matu-inĩ, Hũnia ngoro yakwa,\n" +
                "Honia ngoro yakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nHingũrĩra rĩu gĩthima kia mai ma gũtheria,\n" +
                "Ndongoria na itu rĩa Mwaki, rũgendo rũrũ ruothe\n" +
                "We Mũkũũri, we Mũkũũri, Ngo yakwa hinya wakwa,\n" +
                "Ngo yakwa hinya wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdaringa rũĩ rũu rũega nĩngathirũo nĩ guoya,\n" +
                "Ningia nditi cia Jorodani, Nginyia Kanani wega,\n" +
                "Ngagũkumia, ngagũkũmia, ngagũkumia na nyĩmbo,\n" +
                "Ngagũkumia na nyĩmbo.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("7.Reke Njerage Nawe Ngai(NZK 14)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nReke Njerage nawe Ngai, o ta Enoko wa tene,\n" +
                "ũnyite na guoko gwaku, ningĩ tũteretage nawe,\n" +
                "O Na ndaga kuona njĩra, Jesũ ndĩrĩceraga nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNdihota gwĩtwara nyiki, tondũ wa kĩhuhũkanio,\n" +
                "O na marima nĩ maingĩ, Thũ nacio nĩ nyingĩ mũno,\n" +
                "Horeria kĩhuhũkanio, Jesũ ndĩrĩceraga nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdanyita guoko kũu gwaku, nĩngũtiga ikeno cia thĩĩ\n" +
                "Thiĩ na mbere rũgendo-inĩ, bendera nĩ mũtharaba,\n" +
                "O kinya nginye sayuni, Jesũ ndĩrĩceraga nawe.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("8.Ngai Witu Ni Ta Ruaro (NZK 20)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgai witũ nĩ ta rũaro, na irathĩro kwagĩa thĩna,\n" +
                "ũngĩhitha thĩinĩ wake, Ndũrĩ hingo ũgatorio.\n" +
                "\n" +
                "Nĩ rũaro rũa gũtũhonokia, kĩĩruru handũ ha kwĩyũa\n" +
                "Atũtongoria rũgendo-inĩ, nĩtũrĩtooragia ũũru.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩ kĩĩruru kwĩ mũthenya, na gwatuka nĩ rũgiri,\n" +
                "Ndũrĩ ũũru ũgetigĩra, wĩhithĩte thĩinĩ wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nKĩguũ kĩnene kĩa ũũru, kĩahota kũrigicĩria,\n" +
                "No twehitha thĩini wake, Gũtirĩ ũũru tũkona\n");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("9.We Niukeyumia Ruhuho");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWe nĩũkeyũmia rũhuho ruoka, Gwoka rũhuho rũrĩa rũa ũũru,\n" +
                "Rwĩ na makũmbĩ o ta ma iria, nanga yaku nĩĩkarũmia wega.\n" +
                "\n" +
                "Twĩ na nanga ngoro thĩinĩ, ya kũrũmia makũmbĩ moka;\n" +
                "Ya kũhocia hau rũaro-inĩ, rũaro nĩ Jesũ na wendo wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNanga yaku yakorwo nĩ njega, nĩkarũmia yanyitwo nĩ Jesũ,\n" +
                "Ngoro yake yohanio na yaku, nĩũkahootana angĩgũteithia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNanga yaku nĩĩkarũmia wega, kũrĩa guothe ũngĩiguĩra guoya,\n" +
                "O na werũo ũgwati nĩ mũũku, gũtirĩ ikũmbĩ rĩngĩgũthika.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNanga yaku nĩĩkarũmia wega, ũngĩkaigua watonya gĩkuũ-inĩ,\n" +
                "Nditi cia maĩ ma ũũru wothe, Itigatũtoria atũteithia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nRĩrĩa tũkona mũciĩ mũthaka, Wĩ na mĩrango ya ruru nduru,\n" +
                "Tũhocie nanga nginya tũkinye, nginya kĩhuhũkanio gĩthire.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("10.Aria Mendete Ngai(NZK 70)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nArĩa mendete Ngai ũkani tũkene,\n" +
                "Tũine nyĩmbo cia gĩkeno, tũine nyĩmbo cia gĩkeno,\n" +
                "Nĩtũgathe Ngai, nĩtũgathe Ngai.\n" +
                "\n" +
                "Tũrathiĩ Sayuni, nĩkwega mũno Sayuni,\n" +
                "Tũrathiĩ Igũrũ Sayuni, Nĩkuo Ngai atũũraga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩtũkũigua ngoro-inĩ nĩtũrathimĩtwo,\n" +
                "Tũgĩgakinya matu-inĩ, Tũgĩgakinya matu-inĩ,\n" +
                "Kũria kwega mũno kũria kwega mũno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nArĩa matetĩkĩtie matihota kũina,\n" +
                "Arĩa metĩkĩtie noo, Arĩa metĩkĩtie noo,\n" +
                "Mainaga gũkũ thĩ, mainaga gũkũ thĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nHĩndĩ ĩrĩa tũkamuona thĩna nĩũgathira,\n" +
                "Tũkanyua maĩ ma muoyo, tũkanyua maĩ ma muoyo,\n" +
                "Na tũkene mũno na tũkene mũno.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("11.Kristo Muiguaniri Tha (NZK 10)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nKristo mũiguanĩri tha ũhe rũĩmbo ngoro-inĩ,\n" +
                "Tondũ wa irathimo ciaku njiyũrũo nĩ gĩkeno.\n" +
                "Ndũtaga nĩguo ngwendage, na ngũrũmagĩrĩre,\n" +
                "Ngoro yakwa ĩiyũrĩte gĩkeno na mwĩhoko.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgũcokeria Ngai ngatho, nĩ ũndũ wa kũhotithia,\n" +
                "Andongoragia na thayũ o nginya mũciĩ gwake,\n" +
                "Rĩrĩa Jesũ anjaragia ndaarĩ kũraya nake,\n" +
                "Agĩita thakame yake andute ũgwati-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdĩ na thiirĩ mũnene ma tondũ wa kũiguĩrwo tha,\n" +
                "Wendani waku ũnguucie njikarage hamwe nawe.\n" +
                "Nĩguo ndikaguucio rĩngĩ ngũtige we Mũhonia,\n" +
                "Oya ngoro yakwa rĩu ĩtuĩke yaku kũna.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("12.Mwathani nigwedete ma (NZK 10))");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMwathani  nĩngwendete ma, kũrĩ ikeno ciothe,\n" +
                "Tondũ nĩũhete thayũ, Ngoro-inĩ yakwa.\n" +
                "\n" +
                "Ngumo ndĩrĩ yahunjio biũ, Ya wendani waku.\n" +
                "Ngumo ndĩri yahunjio biũ, Ya thakame yaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgũigua wĩ hakuhĩ naniĩ, gũkĩra andũ a thĩ ĩno,\n" +
                "Rĩciiria rĩega no rĩaku, Rĩkĩrĩte rũĩmbo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nũnjĩkĩrĩte gĩkeno ndingĩaga gũkena\n" +
                "Guca nĩ ũndũ ũnyendete, ndingĩigua gĩkeno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nGũkahana atĩa Mwathani, Twatũũrania nawe,\n" +
                "Na tũtwaranage nawe, We Mwathani wakwa.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("13. Jesu Ngwihokete (NZK 123)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ ngwĩhokete, ũtuĩke Mũndwari, O we wiki:\n" +
                "Thikĩrĩria rĩu, Na ningĩ ũtherie,\n" +
                "Ndĩ waku waku ki, kuuma rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nHe hinya wa ngoro, wa kũndeithĩrĩria ndikanagwe:\n" +
                "Tondũ nĩwakuire, Nĩ getha honokie,\n" +
                "Nĩngwendete mũno, Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nGũkũ nĩndangagwo, nĩ mathĩna maingĩ, O na kĩeha,\n" +
                "Harĩa he na ndũma, nĩũrĩndongoragia,\n" +
                "Na niĩ ngume thutha, Mwathi wakwa.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("14. Nindiyoneire Githima (NZK 188)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩndĩyoneire gĩthima Nakĩo nĩ gĩtune,\n" +
                "Nĩ kĩriku na nĩ kĩariĩ Na nĩ gĩa thakame.\n" +
                "\n" +
                "Ya gũtheria nduĩke mwega, Nderikia ho nĩngũthera\n" +
                "Nĩngũgoca Mwathi Jesũ, Nĩatheragia, nĩatheragia\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRĩu nĩngarũrũkĩte, nĩngũtuĩka mwerũ,\n" +
                "Mũndũ ũria wakwa wa tene nĩagwe thakame-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdongoragio nĩ ũtheri, ũrĩa wa matu-inĩ,\n" +
                "Ngoro ĩtherete kũna ndĩhumbĩte Kristo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNĩ wega wa kũgegania Gũikio thakame-inĩ,\n" +
                "Jesũ nowe ũngĩmenya Thakame nĩ yake.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("15.Nindakurirwo ni Mwathani (NZK 35)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩndakũrirwo nĩ Mwathani ũria wanjiguirĩire tha,\n" +
                "Thogora wa thakame yake nĩũnduĩte mwana wake.\n" +
                "\n" +
                "Nĩngũũrĩtwo! Nĩngũũrĩtwo na thakame;\n" +
                "Nĩngũũrtwo! Nĩngũũrĩtwo na thakame.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩndakenirio nĩ gũkũũrwo, Gĩkeno gĩtangĩgwetwo;\n" +
                "Na nĩkuo kuonanirie wendo ũrĩa wanduire wake ki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩngerorera wega wake, Mũnene wa kũgegania;\n" +
                "Ndĩkenagia na watho ngĩinagĩra rĩĩtwa rĩake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNĩnjũĩ nĩ njigiirwo  thũmbĩ, ya muoyo kũrĩa matu-inĩ;\n" +
                "Hatigairie hanini acooke nĩguo harĩa arĩ nginye ho.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("16.Jesu Njikaria Hau Mutharaba-ini (NZK 120)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ njikaria hau Mũtharaba-inĩ,\n" +
                "He na rũĩ rũa muoyo rũa gũthambia mehia.\n" +
                "\n" +
                "Mũtharaba waku noguo ndĩkumagia,\n" +
                "O kinya ngahurũka, o kũu matu-inĩ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nHau mũtharaba-inĩ he na tha na wendo,\n" +
                "Na njata ya rũcinĩ ĩnjaragĩra ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nHau mũtharaba-inĩ Gatũrũme ka Ngai\n" +
                "Nĩkangũũrĩirie wega ũrĩa anyendete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nHau mũtharaba no ho ngweterera\n" +
                "Ndĩ na kwĩrĩgĩrĩra nginye o matu-inĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("17. Kuria Guothe Angienda Thii");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nKũrĩa guothe angĩenda thiĩ, ndikarega o rĩ o rĩ,\n" +
                "Tondũ nĩngũririkana, we nĩanguĩrĩire kalwarĩ.\n" +
                "\n" +
                "Jesũ nĩarĩndongoragia, ũtukũ o na mũthenya,\n" +
                "Nĩ mwendwa wakwa wa ngoro, Ndaririkana Kalwarĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩnyendete watho wake, Anyitĩte guoko gwakwa,\n" +
                "Nĩngenagio nĩ thakame iyo yaitirũo Kalwarĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNganja o na guoya ndirĩ, Mũhoonokia e hakuhĩ,\n" +
                "Njĩrĩgĩrĩire ngamuona, Nĩ mũrata wa Kalwarĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("18. Warora Mutharaba Wa Jesu (NZK 120)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWarora mutharaba wa Jesu, nĩukuona muoyo ti itheru\n" +
                "Riiri wa thĩ o na utonga, Ndiracona thengia mutharaba.\n" +
                "\n" +
                "Warora, mutharaba nĩukwĩgĩĩra na muoyo\n" +
                "Mutĩ-inĩ wa Kalwarĩ, Na ndukurĩhio kĩndu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNdacuthĩrĩria ngona Jesu ho, Nguigua ta ngwenda gukena,\n" +
                "Kĩbii kĩa magerio gĩoka ndĩonaga hinya mutharaba-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdĩroraga mutharaba wake ndĩhokee Ngai wakwa,\n" +
                "Ndangĩreka ngwe nĩ magerio Tondu nĩndĩrĩmwathĩkagĩra.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("19. Mwathani Nii Nimbataire (NZK 116)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMwathani niĩ nĩ mbataire, ũthambie gĩko gĩthire,\n" +
                "Na mwaki kana na maĩ ũtherie biũ ũtherie biũ.\n" +
                "\n" +
                "ũtherie biũ Mũhonokia, thĩinĩ na nja ngoro yothe,\n" +
                "Ona angĩkorũo no mwaki kinya ngũĩre mehia makwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩ ndĩkenaga mũno ma, wanyonereria maũndũ,\n" +
                "No ũrĩa mbatairo nĩguo no ngoro, yakwa ĩthere.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNgoro yakwa ĩtarĩ theru ndingĩhota gũkũũrana,\n" +
                "Roho waku we Mwathani, Theria rĩu, Thambia rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNgeretie na hinya wothe gũtiga meciria moru,\n" +
                "Na ndingĩhota gũtiga nĩnemetwo nĩ gwĩtheria.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("20. Mehia Maku Mangikorwo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMehia maku mangĩkorũo mahaana gakarakũ,\n" +
                "Nĩũgũtherio mothe mothe ũhaane ta ira.\n" +
                "O na makorũo nĩ matune, O ta thakame.\n" +
                "O na matuĩka matune, O na matuĩka matune,\n" +
                "Mũno ta gakarakũ, ũkahaana ira.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMũgambo ũyũ ũgũgwĩta ũcokerere Ngai, \n" +
                "Nĩ Ngai wa matha maingĩ, O na wendani mũingĩ.\n" +
                "Igua mũgambo ũcio wake, Coka harĩ we.\n" +
                "Igua mũgambo ũcio wake, Igua mũgambo ũcio wake,\n" +
                "Ndũcokerere Ngai, Ndũcokerere Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩũkũrekerũo ũremi waku wa hĩndĩ ndaya,\n" +
                "Na ndũkaũririkana rĩngĩ hingo ya muoyo.\n" +
                "Nĩũkũrekerũo remi waku, wa hĩndĩ ndaya,\n" +
                "Na ndũkaũririkana, Na ndũkaũririkana,\n" +
                "ũcio wa hĩndĩ ndaya, ũcio wa hĩndĩ ndaya.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("21. Ningwenda Kumenya Jesu (NZK 120)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩngwenda kũmenya Jesũ, ngirĩrĩrie kũmũmenya,\n" +
                "Menye wendani wake, na ũhonokio ũrĩa mũiganu.\n" +
                "\n" +
                "Makĩria makĩria, menyage Jesũ,\n" +
                "Menye wendani wake na ũhonokio ũrĩa mũiganu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNa nĩngwenda nyone Jesũ na ngirĩrĩrie kũmũigua,\n" +
                "Akĩara Ibuku-inĩ rĩake, eyonithanie kũrĩ niĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩngwenda kũmenya wega; ngirĩrĩrie gũkũũrana,\n" +
                "ũrĩa we mwene endaga njĩkage ũrĩa ũmũkenagia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNĩngwenda njikare nake, matereta-inĩ makĩria,\n" +
                "Na ngirĩrĩrie kuonia andũ arĩa angĩ ũhonokio wake.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("22. Nguinira Wendo Wake (NZK 31)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgũinĩra wendo wake, wendo wa Jesũ wakwa,\n" +
                "Atũire anyendete, nginya gũkua Kalwarĩ.\n" +
                "\n" +
                "Ngũinĩra wendo wake, Ngũkumia wendo wake.\n" +
                "Akuire nyone muoyo, Ngũinĩra wendo wake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩarĩrire maithori na ndirĩ ndĩrarĩra,\n" +
                "O nakuo gũthaithana, Nĩathaithanagĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nIgũrũ na thĩ hihi, Mwamenya mehia makwa,\n" +
                "Moru ma, Nowe Jesũ, Nĩathambĩtie mothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNdirĩ ndagwĩka wega, nawe no ũnyendete,\n" +
                "Njoya na ũndũngorie, ũndeithie ngwendage.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("23. Njiira Uhoro Wa Jesu (NZK 34)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNjĩĩra ũhoro wa Jesũ, wandĩke ngoro yakwa,\n" +
                "Njĩĩra ũhoro ũcio mwega Gũkĩra ng’ano ciothe,\n" +
                "Njĩĩra ũria araika, maamũinĩre aciarũo,\n" +
                "Ngai nĩakumio Igũrũ, Thĩ o nakuo kwendanwo.\n" +
                "\n" +
                "Njĩira ũhoro wa Jesũ, Wandĩke ngoro yakwa,\n" +
                "Njĩira ũhoro ũcio mwega, Gũkĩra ng’ano ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nWerũ-inĩ ndarĩire kĩndũ njĩĩra ũrĩa gwatariĩ,\n" +
                "Na tondũ wa mehia maitũ, aagerio akĩhootana.\n" +
                "Njĩĩra wĩra wake  wothe wa kĩeha kĩnene ma,\n" +
                "Kũmenwo o na gũthĩnio, akĩregwo ta mũthĩni.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nHau mũtharaba-inĩ nĩaheirũo ruo rũingĩ,\n" +
                "Njĩra ũhoro wa mbĩrĩra o na kũriũka gwake.\n" +
                "ũhoro ũcio wa wendo, wendo waarĩ mũnene,\n" +
                "Ngũigua ta ngũita maithori, wendo nĩguo wangũrire.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("24. Ngoro Yakwa No Ihote");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgoro yakwa no ĩhote, gũcokeria Ngai ngatho,\n" +
                "No rũĩmbo ngũruta nakũ rũa kũinĩra wendo wa Jesũ.\n" +
                "\n" +
                "Nĩngegete, Nĩngegete, nĩ ũndũ wa wendo wake,\n" +
                "Nĩngegete, Nĩngegete, nĩ wendo wa Mũkũũri.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nGwĩ gĩkeno, kwagĩa thayũ, kwagĩa ũũru, kwagĩa nduma,\n" +
                "Ruo rũagĩa, o na wonje kĩhonia no wendo wa Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nKũrekerũo mehia makwa, ndagwa, ndĩ muoyo, na ndakua,\n" +
                "Ndirĩ na mwĩhoko hangĩ o thengia no wendo wa Jesũ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("25. Utheri Wi Karima-ini (NZK 45)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nũtheri wĩ karĩma-inĩ, ũtheri wĩ iria-inĩ,\n" +
                "Mĩrũri ĩmũrĩkĩte kũrĩa kũruru wega,\n" +
                "No ũtheri ũngĩ mwega ũiyũire ngoro yakwa,\n" +
                "Tondũ Mwathani e thĩinĩ, Ta mĩrũri ya riũa.\n" +
                "\n" +
                "Nĩ ũkengi mwega wa riũa, ũmũrĩkĩte ngoro,\n" +
                "Jesũ athekia hanini, Kĩeha no gĩgathira.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgũgũithia kĩeha tĩĩri-inĩ, O ta nguo ndehũku,\n" +
                "ũhumbe gĩkeno gĩaku hotage gũgũkumia,\n" +
                "Mũciĩ ũcio wa matu-inĩ, kũu wendaga njũke,\n" +
                "Meciria na ngoro yakwa, nĩigũkũinamĩrĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMwathani nĩũngũrĩte O hamwe na indo ciakwa,\n" +
                "Tawa ũcio ũnjakĩirie, nĩ wa gũkũgocithia\n" +
                "Mũciĩ ũcio wa matu-inĩ, kũu wendaga njũke\n" +
                "Meciria na ngoro yakwa, nĩigũkũinamĩrĩra.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("26. O Guothe Jesu Eho (NZK 45)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nO guothe Jesũ eho gũtirĩ ũgwati\n" +
                "O guothe andongoretie gũkũ thĩ\n" +
                "O guothe atarĩ ho kũrĩ ũgwati,\n" +
                "O guothe Jesũ eho ndĩngĩtigĩra.\n" +
                "\n" +
                "O guothe, O guothe, Ndirĩ na guoya.\n" +
                "O guothe Jesũ eho ndingĩtigĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nO guothe Jesũ arĩ ho ndirĩ nyiki,\n" +
                "Andũ nomandige no tiguo Jesũ\n" +
                "O na angĩndwara kũrĩa kũrĩ thĩna,\n" +
                "O harĩa Jesũ arĩ nĩ mũciĩ mwega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nO guothe Jesũ eho o na ingikoma,\n" +
                "Kũria ndigiciirio ni nduma nene,\n" +
                "Ngimenyaga ndigacoka gũcanga,\n" +
                "O guothe Jesũ eho ni mũcii mwega.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("27. Tutongorie O Ta Mũriithi (NZK 133)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTũtongorie o ta Mũrĩithi, Jesũ tũmenyerere.\n" +
                "Tũtware nyeki-inĩ nduru, nĩguo tũgũkenagie.\n" +
                "Jesũ witũ, Jesũ witũ, Nĩwe watũgũrire,\n" +
                "Jesũ witũ, Jesũ witũ, Nĩwe watũgũrire,\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRĩu tuĩka mũrata witũ, Na ũtũtongoragie,\n" +
                "Tũgirie gũcoka kwĩhia, twora ũtũũcaragie,\n" +
                "Jesũ witũ, Jesũ witũ, Rĩu tũthikĩrĩrie,\n" +
                "Jesũ witũ, Jesũ witũ, Rĩu tũthikĩrĩrie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩugĩte tũũke kũrĩ we, O na tũrĩĩ ehia mũno,\n" +
                "Tha ciaku nĩigũtuohora nga, Tũtherie twamũkĩre.\n" +
                "Jesũ witũ, Jesũ witũ, Nĩtwagũcokerera,\n" +
                "Jesũ witũ, Jesũ witũ, Nĩtwagũcokerera.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("28. Mwathani Niandongoretie (NZK 18)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMwathani nĩandongoretie, mbatairio nĩ kĩ kĩngĩ?\n" +
                "Tha ciake itirĩ nganja, nĩanginyĩtie hau ndĩ,\n" +
                "Thayũ ũcio wa matu-inĩ nĩguo mwĩhoko wakwa\n" +
                "Marĩa mangĩnjerekera, Jesũ nĩarĩrũngaga,\n" +
                "Marĩa mangĩnjerekera, Jesũ nĩarĩrũngaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMwathani nĩandongoretie, nĩaheaga gĩkeno,\n" +
                "Nadacemania na magerio nĩaheaga mũgate,\n" +
                "ũrĩa wake wa matu-inĩ, ningĩ rĩrĩa ndanyota,\n" +
                "Rũĩ rũkoima rũaro-inĩ, nĩ rũĩ rũa gĩkeno,\n" +
                "Rũĩ rũkoima rũaro-inĩ, nĩ rũĩ rũa gĩkeno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMwathani nĩandongoretie, na nĩanyendete mũno,\n" +
                "Nĩanjĩrĩire kĩhurũko mũciĩ wa Ithe witũ,\n" +
                "Rĩrĩa ngeyona nginyĩte na ndigacoka gũkua,\n" +
                "Ngaina rũĩmbo rũa gĩkeno njuge nĩandongoretie,\n" +
                "Ngaina rũĩmbo rũa gĩkeno njuge nĩandongoretie.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("29. Njagiriirũo Ngoruo Nake (NZK 154)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNjagĩrĩirũo ngorũo nake, Mũhonokia wakwa,\n" +
                "Rĩrĩa e hakuhĩ na niĩ, ngĩaga na hinya ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNjagĩrĩrirũo ngorũo nake, rĩrĩa ndĩna wĩtĩkio;\n" +
                "Kĩhuhũkanio kĩngĩũka, o na ndihu cia iria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNjagĩrĩirũo ngorũo nake, matukũ makwa mothe\n" +
                "O na kũngĩũka, magerio kana thĩna o wothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNjagĩrĩirũo ngorũo nake, njĩra-inĩ ciakwa ciothe;\n" +
                "Maitho make manyonagia, ikĩro ciakwa ciothe.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("30. Undongorie Mwathi Jesu (NZK 73)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nũndongorie Mwathi Jesũ, Njerekeire o matu-inĩ,\n" +
                "Ngũkũhoya ngĩthiaga Mwathi njoya ũndongorie.\n" +
                "\n" +
                "Mwathi njoya ũndongorie na wĩtĩkio wa matu-inĩ,\n" +
                "Kũndũ kwega gwĩ gĩkeno, Mwathi njoya ũndongorie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNdikwenda njikaranagie, na nganja hamwe naguoya,\n" +
                "Arata menda gũikara Niĩ njoya ndoria gwaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNgwenda ndige kuona thĩ ĩno, Shaitani anjikagia mĩguĩ\n" +
                "Ngũmbũka ndĩ na wĩtĩkio, nyinage ta aria atheru.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNgũmbũka ndorete igũrũ, ũkengi nĩũkũndongoria\n" +
                "No ngĩgakinya Mwathani, Ngũkũhoya ũndũngũrie.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("31. Mwathani, Nyita Riu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMwathani, Nyita rĩu, Ndongoria, ũndũgamie,\n" +
                "Ndĩ mũnogu ndirĩ na hinya.\n" +
                "Gwĩ kĩhuhũkanio, gwatuka ũndongorie,\n" +
                "Mwathani, nyita guoko, nyinũkia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nIngĩaga gĩkeno, Mwathani nguhĩrĩria,\n" +
                "Muoyo wakwa ona wathira,\n" +
                "Ndakaya, ndetana, nyita guoko ndikagwe,\n" +
                "Mwathani nyita guoko, nyinũkia.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("32. Oya Ritwa Riu Ria Jesu (NZK 28)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nOya rĩtwa rĩu rĩa Jesũ, we ũnyamarĩkĩte,\n" +
                "Nĩrĩrĩkũhoreragia, Thiĩ narĩo kũndũ guothe.\n" +
                "\n" +
                "Nĩ rĩega, Mũno ma, matu-inĩ na gũkũ thĩ,\n" +
                "Nĩ rĩega, kũndũ guothe, matu-inĩ na gũkũ thĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nOya rĩtwa rĩu rĩa Jesũ, Rĩtuĩkage ngo yaku,\n" +
                "Magerio mangĩgũthĩnia, ũmũhoyage narĩo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nRĩĩtwa rĩa Jesũ nĩ rĩega, Rĩtũkenagia ngoro,\n" +
                "Atũhĩmbĩrie na moko, na ithuĩ tũmũinagĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nRĩrĩa twamũinamĩrĩra, Tũkegũithia harĩ we,\n" +
                "Mũthamaki wa Athamaki, nĩguo tũrĩmwĩtaga.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("33. Jesu Nimbatairio Niwe (NZK 16)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ nĩmbatairio nĩwe, Ndĩ kĩonje ndĩ mũthĩnĩ,\n" +
                "Nyita guoko ũndongorĩe, Na ũhĩngũre maĩtho\n" +
                "\n" +
                "O hingo, O hingo, Jesũ nĩmbataĩrĩo nĩwe,\n" +
                "O hingo, O hingo, Mwathanĩ O ndongoragie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nHumba na ũhorerĩ waku, Ndihumbĩte o mehia,\n" +
                "Na ũnyonie wonje wakwa, Na ũnyonĩe kũhoya\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nWandongorĩa ndĩĩtigĩra, No ndĩ nyiki nĩngũgwa;\n" +
                "Ngwenda tũtwaranage nawe, ũmũrĩkĩre njĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNamo marĩa mangĩndũnga, Mĩtheko kana kĩeha,\n" +
                "Ndĩrĩĩhocagĩa harĩ we, Ngaĩgũa gĩkeno ngoro.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("34. Migambo Yothè Ni Iinire");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMĩgambo yothe ta nĩ ĩĩnĩre, Rĩĩtwa rĩa Jesũ Mwathani!\n" +
                "Mũthamakĩ na nĩngĩ no Ngai, Rĩĩtwa rĩake rĩrĩ ngumo!\n" +
                "\n" +
                "Rĩĩtwa rĩĩ ngumo ,rĩĩtwa rĩĩ ngũmo\n" +
                "Rĩĩtwa rĩa Jesũ Mwathani,\n" +
                "Rĩĩtwa rĩĩ ngumo, rĩĩtwa rĩĩ ngumo,\n" +
                "Rĩĩtwa rĩa Jesũ rĩĩ ngumo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRĩninaga gũoya wa ngoro-inĩ, Rĩĩwa rĩa Jesũ rĩĩ ngũmo!\n" +
                "O na mwĩhia noarĩtĩkagia, Rĩĩtwa rĩa Jesũ rĩĩ ngũmo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩrĩninaga guoya wa mehia, Rĩĩtwa rĩa Jesũ rĩĩ ngumo!\n" +
                "Atheranagia ki na thakame, Rĩĩtwa rĩa Jesũ rĩĩ ngumo.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("35. Ndi Ruimbo Nyendete Kuina (NZK 32)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNdi rũimbo nyendete kũina, Kuuma ndarutwo mehia-inĩ,\n" +
                "Nyinagĩra Mũkũũri wakwa Na Mũthamaki wakwa\n" +
                "\n" +
                "Tondũ nĩngũũrĩtwo, Tondũ nĩngũũrĩtwo,\n" +
                "Ngũgoca rĩĩtwa rĩake, Tondũ nĩngũũrĩtwo\n" +
                "Ngũgoca rĩĩtwa rĩũ rĩake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nKristo nĩanjĩganĩte kuuma ndarutwo mehia-inĩ,\n" +
                "Ndĩmũtungatage na kũmũigua Tondũ nĩangũũrĩte.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdĩ mũira ũtarĩ nganja kuuma ndarutwo mehia-inĩ,\n" +
                "Ndirĩ guoya kana nganja Tondu niĩ nĩanguurĩte.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("36. Jesu Mukuuri Wakwa");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ Mũkũũri wakwa, we gĩkeno gĩakwa,\n" +
                "Nowe ũhoragĩria, Rĩrĩa ndagĩa na kĩeha.\n" +
                "\n" +
                "Jesũ Mũkũũri wakwa, Nowe ndĩĩnagĩra,\n" +
                "Ndirĩ na mũrata ũngĩ, ũngĩnyenda o tawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNowe mwĩhoko wakwa, mĩaka yakwa yothe,\n" +
                "Ndanoga na ndarĩra Nĩũrĩhoragĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nWe nowe ndĩhokete ũndongorie wega,\n" +
                "We nowe mwendwa wakwa Ndũhana arata arĩa angĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nRĩrĩa ngũina ngenete Gũkũ thĩ ya mehia,\n" +
                "Nĩ tondũ njetereire Gũtonya kũu Igũrũ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        song=new Song();
        song.setTitle("37. Gutiri Na Murata Ta Jesu (NZK 44)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nGũtirĩ na mũrata ta Jesũ, Gũtirĩ o na ũmwe,\n" +
                "ũngĩhonia mĩrimũ ya ngoro, Gũtirĩ o na ũmwe\n" +
                "\n" +
                "Jesũ nĩoĩ mathĩna maitũ, Nĩarĩtũtongoragia,\n" +
                "Gũtirĩ na mũrata ta Jesũ, Gũtirĩ o na ũmwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nGũtirĩ ũngĩ mũthingu take, Gũtirĩ o na ũmwe,\n" +
                "Nũũ wanona mũhoreri take, Gũtirĩ o na ũmwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nWanona mũrata ũngĩ take, Gũtirĩ o na ũmwe,\n" +
                "ũtũkũ ndangĩgũtiga wiki, Gũtirĩ o na ũmwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nKwĩ mwana wanaregwo nĩ Jesũ, Gũtirĩ o na ũmwe,\n" +
                "Kana mwĩhia wanaregwo nĩwe, Gũtirĩ o na ũmwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nTũrĩ twaheo kĩheo take, Gũtirĩ o na ũmwe,\n" +
                "Hihi agiria tũthĩĩ matu-inĩ, Gũtirĩ o na ũmwe.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("38. Muhonokia E Hakuhi (NZK 157)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũhonokia e hakuhĩ, Na nĩwe watũkuĩrĩire,\n" +
                "Arĩa maandĩkĩtwo igũrũ, Nĩo akongania.\n" +
                "\n" +
                "Hakuhĩ, Hakuhĩ, E hakuhĩ o mũromo-inĩ,\n" +
                "Nĩegũũka, Nĩegũũka, Arũngiĩ mũromo-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMarũri no maratuonia, Atĩ rĩũ e hakuhĩ,\n" +
                "Nao andũ ake aamũre, Nĩmamwetereire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nThĩ ĩno mbaara ndĩgathira, Gĩkeno thayũ gũtirĩ,\n" +
                "O kinya Jesũ acoke, kũnina waganu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nThĩ ĩno yarĩkia gũthambio, tũgacoka gũtũũra kuo,\n" +
                "Tũtũũre mĩndi na mĩndi, Tũtigakua rĩngĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("39. Huhai Coro Mugambie Muno (NZK 161)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nHuhai coro mũgambie mũno, Jesũ nĩegũũka rĩngi,\n" +
                "Kũngũĩriai na mũine mũno, Jesũ nĩegũũka rĩngi.\n" +
                "\n" +
                "Nĩegũcoka, nĩegũũka,\n" +
                "Jesũ nĩegũka rĩngi\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nGambia irĩma-inĩ ona werũ-inĩ, Jesũ nĩegũũka rĩngĩ,\n" +
                "Ena ũkengi, Mũkũũri witũ, Jesũ nĩegũũka rĩngi.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nGambia irĩma-inĩ ona ndihũ-inĩ, Jesũ nĩegũũka rĩngi,\n" +
                "Na ngurunga-inĩ, mũgambie coro, Jesũ nĩegũka rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nMĩnyamaaro nĩ ĩgũtũmenyithia, Jesũ nĩegũũka rĩngĩ,\n" +
                "Na ndũrĩrĩ nĩirokanĩrĩra, Jesũ nĩegũũka rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nNdũrĩrĩ  nĩmbucunu na nĩguo, Jesũ nĩegũũka rĩngĩ,\n" +
                "ũũgĩ nĩ mũingĩ ona mĩhang’o, Jesũ nĩegũũka rĩngĩ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("40. Nduma Niigathira Jesu Oka ( NZK 183)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNduma nĩĩgathira Jesũ oka, nduma nĩĩgathira Jesũ oka,\n" +
                "Nduma nĩĩgathira Jesũ oka, Kũũngania arata ake.\n" +
                "\n" +
                "Kũũngania arata ake, Kũũngania arata ake,\n" +
                "Nduma nĩĩgathira Jesũ oka, Kũũngania arata ake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nKĩeha gĩgathira Jesũ oka, kĩeha gĩgathira Jesũ oka,\n" +
                "Kĩeha gĩgathira Jesũ oka, Kũũngania arata ake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nRuo rũgathira Jesũ oka, Ruo rũgathira Jesũ oka,\n" +
                "Ruo rũgathira Jesũ oka, Kũũngania arata ake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNa nĩtũkageithia Jesũ oka, na nitũkageithia Jesũ oka,\n" +
                "Mũcemanio-inĩ ũcio Jesũ oka, kũũngania arata ake. ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("41. Niegũũka O Ringi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩegũka o rĩngĩ, aiyĩre andũ ake,\n" +
                "Amatware gwake mũciĩ matũũranie nake.\n" +
                "Roria maitho igũrũ, ũkĩinaga nyĩmbo,\n" +
                "Egũũka o ta Mũnene atũhonokie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nThĩ nĩĩgathingitha, na matu mathengio\n" +
                "Mũndũ mwĩhia akarora kũ nĩũndũ wa kũmaka,\n" +
                "Ngũũria ũgeka atĩa waaga gwa kwĩhitha.\n" +
                "Mwathani nĩakerĩhĩria tha ciake ciaregwo\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nArĩa matamũũĩ nĩmakamũmenya,\n" +
                "Maitho make no ta mwaki harĩ mũndũ mwĩhia,\n" +
                "Arĩa mamũthũire na mamũcambagia,\n" +
                "Makĩmenaga andũ ake makarĩra tũhũ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("42.  Onei Agiuka Riera-ini");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nOnei agĩũka rĩera-inĩ, Jesũ ũrĩa woragirũo,\n" +
                "Andũ atheru metereire, Oke amatware gwake,\n" +
                "Halleluyah! Halleluya! Jesũ nĩwe Mũthamaki,\n" +
                "Halleluyah! Halleluya! Jesũ nĩ Mũthamaki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nũhonokio wanagĩũka, ũtũire wetereirũo,\n" +
                "Andũ a Ngai arĩa mathũirũo, Makaiyũkio rĩera-inĩ\n" +
                "Arata aitũ, arata aitũ, mũthenya rĩu nĩ mũkĩnyu.\n" +
                "Arata aitũ, arata aitũ, mũthenya nĩ mũkinyu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nCoro warĩkia kũhuhwo matu na thĩ cieherio,\n" +
                "Arĩa othe mamũthũire makagirĩka mũno,\n" +
                "Makeragwo, Makeragwo, rĩu nĩmũgũcirithio,\n" +
                "Makeragwo, makeragwo, ũkai mũcirithio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nAndũ othe nĩmakamuona e na ũkengi mũingĩ,\n" +
                "Arĩa mamũthecangire makĩmwamba mũtĩ-inĩ,\n" +
                "Makarĩra, makarĩra, mona agĩũka rĩera-inĩ,\n" +
                "Makarĩra, makarĩra, mona agĩũka rĩera-inĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("43. Gwatiai Matawa Manyu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nGwatiai matawa manyu tondũ nduma nĩgũũka,\n" +
                "Nduma nene kũndũ guothe Mwathani atanacoka.\n" +
                "\n" +
                "Gwatiai matawa manyu\n" +
                "Mũrĩ na guoya mũnene,\n" +
                "Mwathani e hakuhĩ gũũka,\n" +
                "Matawa nĩ magwatio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nAndũ ngiri nyingĩ rĩu maikare makomete,\n" +
                "Ithuĩ na ithuĩ twĩhokete atĩ no egũcoka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nCiugo ciake nĩcio tawa na tũtikahĩtithio\n" +
                "Ona mogwati mangĩũka ndagatũrekereria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nCiĩko na wĩtĩkio nĩcio ciagũtooria thũ ciitũ\n" +
                "Twathĩka no tũrathimwo twaga kũgĩa na nganja.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("44. Angigoka Riria Ruoro (NZK 163)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nAngĩgoka rĩrĩa ruoro rũtemaga\n" +
                "Kwarĩkia gũkia, oke akengete ma,\n" +
                "Na riiri mũingĩ, nĩagatwamũkĩra na tũkene\n" +
                "Tũkĩambata gwake.\n" +
                "\n" +
                "Jesũ nĩ nginya rĩ, Tũgũgweterera hihi,\n" +
                "Ndũkĩhiũhe, Halleluya,\n" +
                "Halleluya! Amen. Halleluya! Amen.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nAngĩgoka kwĩ mũthenya barigici\n" +
                "Kana hihi oke gũtukĩte mũno,\n" +
                "Tuone ũtheri ũhaana ta mũthenya mũnene\n" +
                "Tũkĩambata gwake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nTũkaigũa rũĩmbo, Hosana Mũthamaki,\n" +
                "Rũa andũ atherũ hamwe na araika,\n" +
                "Nake Mwaathani akengete gũkena mũno-ma.\n" +
                "Tũkiambata gwake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nTũkahungura matu mairũ na njata\n" +
                "Gĩkuũ na thĩna, o na mĩrimũ yothe.\n" +
                "Kũmenwo, ruo, kĩrĩro nĩigathira o kũna\n" +
                "Tũkĩambata gwake");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("45. Ningenagio Nĩ Kwihoka (NZK 129)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩngenaga nĩ kwĩhoka, Mũhonokia makĩria,\n" +
                "Ndamwĩtĩkia nĩ nyonete, kĩhurũko ngoro-inĩ.\n" +
                "\n" +
                "Jesũ nĩngwĩhokete ma, nĩnyonete wĩ njamba;\n" +
                "Jesũ wĩ ma hingo ciothe, ciĩranĩro-inĩ ciaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩngenagio nĩ kwĩhoka, Mũhonokia makĩria,\n" +
                "Gwĩtĩkia thakame yake, nĩthambĩtio wothe biũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩngenagio nĩ kwĩhoka, Mũhonokia makĩria,\n" +
                "Nĩaheaga hĩndĩ ciothe,  muoyo wake na thayũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNĩngenagio nĩ kwĩhoka, Mũhonokia makĩria,\n" +
                "Jesũ mwendwa na mũrata, njĩkarĩa hĩndĩ ciothe.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("46. Ndagia Gikeno Na Ngwataniro (NZK 43)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNdagia gikeno na ngwatanĩro ingĩĩhokaga Jesũ wiki;\n" +
                "He kĩrathimo ningĩ na thayũ, ingĩĩhokaga Jesũ wiki.\n" +
                "\n" +
                "Ndamwĩhoka, ndicoka kuona ũgwatĩ;\n" +
                "Nĩngwĩhoka, nĩngwĩhoka Mwathani Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩndĩhotaga rũgendo rũrũ, ingĩĩhokaga Jesũ wiki;\n" +
                "Nayo njĩra nĩ ĩrĩhũthagĩra, ingĩĩhokaga Jesũ wiki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdirĩ na ũndũ wa gwĩtigĩra, ingĩĩhokaga Jesũ wiki;\n" +
                "Nĩarikoragwo hakũhĩ na nĩĩ, ingĩĩhokaga Jesũ wiki.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("47.  Wi Munogu Na ũgatangika (NZK 124)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWĩ mũnogu na ũgatangĩka, Thiĩ wĩre Jesũ  mathina maku,\n" +
                "Nĩũrũmbũyagia ikeno ithiraga, Thiĩ wĩre Jesũ wiki.\n" +
                "\n" +
                "Thiĩ wĩre Jesũ mathĩna maku, Nĩ mũrata waku ki,\n" +
                "Gũtirĩ na mũrata ta Jesũ, Thiĩ wĩre Jesũ wiki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMaithori nĩ maranyũrũrũka, Thiĩ wĩre Jesũ mathĩna maku,\n" +
                "Kana nĩ mehia maragũthĩnia, Thiĩ wĩre Jesũ wiki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩũretigĩra thĩna na kĩeha, Thiĩ wĩre Jesũ mathĩna maku,\n" +
                "Kana nĩũtangĩkagĩra rũciũ, Thiĩ wĩre Jesũ wiki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nWecirĩria gĩkuũ nĩũmakaga, Thiĩ wĩre Jesũ mathĩna maku,\n" +
                "Nĩũcaragĩa ũthamaki wake, Thiĩ wĩre Jesũ wiki.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("48. We Roho Mutheru Ma (NZK 41)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWe Roho Mũtheru ma, We mũtongoria wa ma,\n" +
                "ũtũnyite moko riu rũgendo-ini rũrũ.\n" +
                "Tũigue mũgambo waku, Mũgambo mũhoreri,\n" +
                "“Mũgendi ũka hari nii,  ningũgũkinyia mũcii.”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nWe nowe murata ki, uteithio wa  hakuhi\n" +
                "Nina nganja na guoya, Hindi iria twi nduma-inĩ,\n" +
                "Tũigue mũgambo waku, Mũgambo mũhoreri,\n" +
                "“Mũgendi ũka hari nii,  ningũgũkinyia mũcii.”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nWira witu wathira, Tũninirwo minoga,\n" +
                "Twiriragirie igũrũ, Na ngatho na mahoya,\n" +
                "Tũigue mũgambo waku, Mũgambo mũhoreri,\n" +
                "“Mũgendi ũka hari nii,  ningũgũkinyia mũcii.”");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("49. We Roho Mutheru Uka (NZK 40)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWe Roho Mũtheru ũka ũthambie ngoro yakwa\n" +
                "ũtherie meciiria makwa ũka ũnjiyũre rĩu\n" +
                "\n" +
                "We Roho Mũtheru ũka ũnjiyũre rĩu,\n" +
                "ũtherie meciria makwa ũka ũnjiyũre rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nOna ingĩaga gũkũona nĩũnjiyũraga ngoro,\n" +
                "Naniĩ nĩ mbatairio nĩwe ũka ũnjiyũre rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdirĩ na hinya mũiganu nĩngũkũinamĩrĩra,\n" +
                "Roho Mũtheru wa Ngai ũnjiyũrie hinya rĩu\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nũthambie na ũhorerie ũhonie ũndathime,\n" +
                "ũnjiyũre ngoro yakwa tondũ nĩwe Mũhoti.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("50. Mwaki Uria Wakanaga (NZK 80)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMwaki uria wakanaga ngoro-inĩ cia andũ arĩa atheru,\n" +
                "Wiha rĩu wakanage Thĩini witũ kwagia thĩna.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRoho wa Aburahamu  eha Mwĩkanĩri rũũri rũaku,\n" +
                "Nĩororoirie Paul Akĩmũhe hinya mwerũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nRoho ũcio nĩ wa tene, Nĩ mũrutani wa wendo,\n" +
                "Nĩateithĩrĩirie Isaia, Na agĩtongoria Daudi.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nWega waku wĩha rĩu, ũrĩa wonirie Elija,\n" +
                "Musa agĩkenga ta riũa, Ayubu akĩũmĩrĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nRirikana tene tene Werũhie wĩra waku Ngai,\n" +
                "Twahingũra ngoro ciitũ, Tũitĩrĩrie Roho waku.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("51. Jesu Uugite Eri Kana Atatu (NZK 13)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ uugĩte erĩ kana atatũ\n" +
                "Mongana magwĩte no ũkamaigua,\n" +
                "Nĩtũgwĩtĩkie We, Nĩtwakũhoya,\n" +
                "ũũka Jesũ rĩũ ũtũrathime.\n" +
                "\n" +
                "ũka ũtũrathime, Nĩtũgwetereire,\n" +
                "Ndũgĩũke ũtũrathime, Tũkuhĩrĩrie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nJesũ nĩũcemanagia hamwe na ithui,\n" +
                "O na rĩu ũka ũtũceerere\n" +
                "Tondũ wĩ mũrathime ũtwamũkĩre,\n" +
                "Tũhe wega waku nĩtwagũthaitha.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nJesũ tũhe mĩgambo ĩrĩa mĩega\n" +
                "Tũkũinĩre nayo tũgũthathaiye,\n" +
                "Tũhe wĩtĩkio mũingi O na mwĩhoko\n" +
                "Tũhe wendo waku ũrĩa mũtheru.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("52. Mwathani Amumenyagirire (NZK 50)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMwathani amũmenyagĩrĩre O nginya tuonane rĩngĩ,\n" +
                "Mũikare kiugũ-inĩ gĩake O nginya tũkonana rĩngĩ.\n" +
                "\n" +
                "O nginya tũkonana rĩngĩ, Magũrũ-inĩ ma Jesũ\n" +
                "O nginya tũkonana rĩngĩ, Mwathani nĩamũmenyerere.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMwathani amũmenyagĩrĩre, ruungu rua mathagu make\n" +
                "Mũrĩage irio cia Mana, O nginya tũkonana rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMwathani amũmenyagĩrĩre ũũru wathimba ta matu\n" +
                "Amũkumbatĩre wega, O nginya tũkonana rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nMwathani amũmenyagĩrĩre Bendera ya wendo wake,\n" +
                "ĩikare igũrũ rĩanyu, O nginya tũkonana rĩngĩ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("53. Riu Nikuratuka ( NZK 92)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nRĩu nĩkuratuka, Andu othe mahuruke,\n" +
                "Nĩtugucokia ngatho\n" +
                "Tuona njata na mweri, ikengete.\n" +
                "\n" +
                "Ngai wĩ Mũtheru, Wĩ Mũnene,\n" +
                "Wathaga kũndũ guothe,\n" +
                "Thĩ-ĩno ona matu-inĩ, Wĩ Mũtheru\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nWe Mũgai wa muoyo, Wathaga kũndũ guothe\n" +
                "Tũmenyerere wega, Tondũ We wĩ hakuhĩ\n" +
                "Nginya gũkĩe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nWendo waku mũnene, ũtũikũrũkĩre,\n" +
                "Twarĩrie wega waku, Ta njata cĩa matu-inĩ\n" +
                "Nginya gũkĩe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNa rĩrĩa ũgoka thĩ, ũtũtware Igũrũ\n" +
                "We Ngai wa araika, Tũgatũũrania nawe\n" +
                "Nginya tene.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("54. Ihinda Ria Kuhoya)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nIhinda rĩa kũhoya Nĩrĩo ngoro ciitũ\n" +
                "Ciũnganaga he Jesũ Mũrata witũ\n" +
                "Ona nĩtwĩtĩkĩtie ũgitĩri wake\n" +
                "Ngoro yothe hehenje no ĩkahonio wega.\n" +
                "\n" +
                "Tũkĩmũhoya, Tũkĩmũhoya,\n" +
                "Ngoro yothe hehenje, no ĩkahonio wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nIhinda rĩa kũhoya, Nĩrĩo Mũhonokia\n" +
                "Aceeragĩra ciana ciake ciamwita\n" +
                "Nake agaciarĩria, agacihuurũkia,\n" +
                "Ngoro yothe hehenje no ĩkahonio wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nIhinda rĩa kũhoya, Arĩa mageretio\n" +
                "Nĩrĩo Manengagĩra Jesũ mathĩna\n" +
                "Nake  ena tha nyingĩ akamehereria\n" +
                "Ngoro yothe hehenje no ĩkahonio wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nIhinda rĩa kũhoya, Nĩrĩo tũmenyaga\n" +
                "ũrĩa wothe twahoya We nĩekũhingia\n" +
                "Namo mathĩna maitũ agakuua mothe,\n" +
                "Ngoro yothe hehenje no ĩkahonio wega.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("55. Njigua Ngikaya (NZK 8)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNjigua ngĩkaya, Njiguĩra tha, Ndũhiũhe, Mũkũũri Mũnene,\n" +
                "Ngoro yakwa rĩu nĩwe yetereire, ũka ũka rĩu\n" +
                "\n" +
                "Ndũire njũrũraga irĩma-inĩ,\n" +
                "Ndorire ngiuma gwitũ mũcĩĩ\n" +
                "Njoya ũnjokie kiugũ-inĩ giaku kĩega\n" +
                "ũka ũka rĩu\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNdaraga nja Ndirĩ nyũmba, Ndũkĩhiũhe, Mũkũũri Mũnene,\n" +
                "Ingĩkuona no njigue ta ndĩ na muoyo, ũka ũka rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNgũigua ndĩ nyiki, O na nganoga, Hiũha, Mũkũũri Mũnene,\n" +
                "Ngwenda gũkuona, Na tũcemanie nawe, ũka ũka rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNdũgite ngoro Hehenjeku, Ndũhiũhe, Mũkũũri Mũnene,\n" +
                "Njigua ngĩkaya, ngũkũhoya ũhiuhe, ũka ũka rĩu. ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("56. Jesu Ni Unyendete (NZK 30)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ nĩũnyendete, Nĩngũteng’era gwaku\n" +
                "Nĩwe ũnjiganĩte ũrĩa mũũru angĩũka.\n" +
                "Mbaru-inĩ ciaku Jesũ ũhithe muoyo wakwa,\n" +
                "ũhithe Mũhonokia, Honokia ngoro yakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNdirĩ hangĩ ngwĩhitha, we nowe ndĩĩhokete\n" +
                "Ndũkandige Mwathani, ũnjiguĩre tha Jesũ,\n" +
                "Niĩ nĩngwĩtĩkĩtie, Nĩwe ũhotithagia,\n" +
                "Rĩrĩa ndona ũgwati, mbaara-inĩ tũrĩ o nawe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩmbataragio nĩwe, nĩũmenyagĩrĩra\n" +
                "Rĩrĩa njagĩte kĩndũ, We nĩwe ũndeithagia\n" +
                "Teithia arĩa maragwa, Cionje ũcihe hinya\n" +
                "Honia arĩa marũarĩte, Tongoria atumumu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nThakame ĩo yaku ĩtheretie  wega ma\n" +
                "Nũiyũrĩwo nĩtha, nacio igatũigana\n" +
                "Gwaku Mwathani nĩkuo, Gwĩgĩthima kĩa muoyo,\n" +
                "Therũka ngoro-inĩ yakwa tene na tene.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("57. Mithenya Itandatu Riu (NZK 81)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMĩthenya ĩtandatũ rĩu nĩ mĩthiru Thabatũ ĩgooka\n" +
                "Ngooro yakwa gĩkenere mũthenya ũyũ wa Ngai witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nKumia Ngai ũrĩa ũheete arĩa anogu kĩhurũko,\n" +
                "Kũhuurũka ngoro ciao Gũkĩra mĩthenya ĩrĩa ĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNgoro ciitũ nĩũkenaga ona igĩcokagia ngatho\n" +
                "Ciiyũrĩtio gĩkeno kĩu gĩa gũkĩra ikeno ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nGĩkeno kĩu kĩa ngoro nĩ rũũri rũa kĩhuurũko\n" +
                "Kĩrĩa kĩigitwo matu-inĩ, gĩakũnina thĩna wothe.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("58. We Ririkanaga Thabatu (NZK 84)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWe ririkana, thabatũ ya Ngai\n" +
                "Nĩguo mũthenya mwega, Gũkĩra ĩrĩa ĩngĩ\n" +
                "ũrehage gĩkeno, O na ũhurũko,\n" +
                "ũkengi waguo wothe, Nĩ ũnene wa Ngai.\n" +
                "\n" +
                "ũka rĩu, ũka rĩu, Thabatũ ndathime,\n" +
                "Kuhĩrĩria, kuhĩrĩria, Thabatũ njega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nũtue nĩ Mũtheru, ũmũhoe, Rĩu,\n" +
                "ũrĩa werire arutwo, Niĩ nĩ niĩ njĩra,\n" +
                "Na tũngĩrũmĩrĩra, Mũhonokia gũkũ,\n" +
                "Nĩtũkaheo muoyo, Wa tene na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMũthenya mũtheru, Tũũhũthĩre wothe\n" +
                "Tũkĩinagĩra Jesũ, Mũrata wa andũ othe,\n" +
                "Mũhonokia mwega ma, Wĩ mwega makĩria,\n" +
                "ũtũũrage ngoro-inĩ, Ciitũ hingo ciothe.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("59. Haria Andu Mahuurukaga (NZK 83)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nHarĩa andũ mahuurũkaga Thabatũ ya Ngai\n" +
                "Ngoro yakwa ĩnguucagia Thiage harĩ Ngai\n" +
                "\n" +
                "Mũthenya…. Mwega ma\n" +
                "Mũthenya…. Mwega ma\n" +
                "Ngoro yakwa ĩnguucagia\n" +
                "Huurũke Thabatũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgoro yakwa nĩkenaga Hingo ya Thabatũ\n" +
                "Ningĩ nĩnyonaga Jesũ Ndakinya harĩ we.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNingĩ Jesũ ni Mwendani Anguucagia wega,\n" +
                "Nĩgetha ndĩmũhe ngoro, Thiage harĩ we.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("60. Huha Coro wa Injiri");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nHuha coro wa Injiri, Iguithia andũ othe,\n" +
                "Nĩguo arĩa megwĩtĩkia, Merire mahonokio.\n" +
                "\n" +
                "Huha coro wa kwĩhũga, Thĩ nyũmũ na iria-inĩ\n" +
                "Ngai nĩwe ũgwathĩte, nĩguo arĩa ohe mohorwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nAnĩrĩra irĩma-inĩ, Miungu-inĩ na werũ-inĩ,\n" +
                "O na iria-inĩ guothe, Nĩmareheirwo muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nAnĩrĩra ng’ongo ciothe, Na kũrĩa gũtumanu,\n" +
                "Nĩmathondekeirwo handũ, Ngai nĩametereire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nHuha tondũ andũ aingĩ, Nĩmekwenda mohorwo,\n" +
                "Mwathani nĩekũmera, “ũkai kũrĩ niĩ.”");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("61. Ngatuura Rũĩ-inĩ Rũa Muoyo (NZK 181)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgatũũra rũũĩ-inĩ rũa muoyo, Rũrĩa rũkengete,\n" +
                "Harĩa Jesũ atwetereire Atũkũngũĩre.\n" +
                "\n" +
                "Ngatũũra handũ he na maĩ, maĩ marĩa me na muoyo,\n" +
                "Njikaranagie na Jesũ, Nĩwe maĩ ma muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRĩrĩa twanogio nĩ rũgendo Nĩtũhurũkage,\n" +
                "Harĩa he na maĩ ma muoyo, ũnyue ũhonoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nWe ũnyotie ũka kwĩ Jesũ nĩegũkũnyotora,\n" +
                "Jesũ nĩ maĩ ma muoyo, ũnyue ũhonoke.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("62. Njoya Uguo Ndarii Jesu (NZK 140)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNjoya ũguo ndarĩi Jesũ Nĩwanjitĩire thakame.\n" +
                "Na tondũ nĩũnjĩtĩte Jesũ njoya njoya, rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nTi wĩra wakwa gwĩtheria Nĩwe ũgũtheria kũna,\n" +
                "Nĩwe waitire thakame Jesũ njoya njoya rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdirĩ ndona gĩkeno thĩ No guoya na mbaara nene\n" +
                "We Gatũrũme ka Ngai, Jesũ njoya njoya rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nũtumumu wakwa wothe, ũkĩa wakwa na gwĩtanga\n" +
                "Nĩũkũnjehereria mothe Jesũ njoya njoya rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nTondũ wa ciĩrĩro ciaku Theria rĩu ũndekere,\n" +
                "Nyamũkĩra nduĩke waku Jesũ njoya, njoya rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t6.\nWendo waku noguo wiki ũtooretie ngoro yakwa,\n" +
                "Rĩu ngũtuĩka waku ki Jesũ njoya njoya, njoya.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("63. Ndukandige Muhonokia (NZK 22)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNdũkandige Mũhonokia, riu thikĩrĩria,\n" +
                "ũgĩũka gũcerera arĩa angĩ, ndũkahĩtũke.\n" +
                "\n" +
                "Jesũ Jesũ, njigua Mwathani\n" +
                "ũgĩũka gũcerera  arĩa angĩ, ndũkahĩtũke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nGĩtĩ gĩaku kĩrĩa gĩa tha, nĩkĩo ndorete,\n" +
                "Ndurĩtie ndu o gĩtĩ-inĩ, nĩguo njoherũo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdĩrĩ ũngĩ ingĩĩhoka, tiga we wiki,\n" +
                "Njerekeria ũthiũ waku, nĩngũkũhoya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNowe wiki Mũhoreria, ndirĩ na ũngĩ,\n" +
                "Igũrũ na thĩ ĩno guothe, ũgũkĩrĩte.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("64. Mundu Aigua Nake Nĩamukĩrie");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũndũ aigua nake nĩamũkĩrie, Hunjia thĩ yothe harĩa yakinya,\n" +
                "ĩria andũ othe gĩkeno gĩkĩ, ũrĩa ũkwenda nĩoke.\n" +
                "\n" +
                "ũrĩa ũkwenda, ũrĩa ũkwenda, Anĩrĩra wega ng’ongo-inĩ ciothe\n" +
                "Ithe witũ ekuuga nĩwe Mũgaĩ, ũrĩa ũkwenda nĩoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nũrĩa wothe mwendi ndagaikare, Nĩ tondũ mũrango nĩmũhingũre Atonye kwĩ Jesũ rũĩ rũ muoyo, ũrĩa ũkwenda nĩoke.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nũrĩa wothe mwendi erĩĩrũo muoyo, Angĩkirĩrĩria magerio wega, Gũtĩrĩ kĩgiria kĩa ahingĩrio,\n" +
                " ũrĩa ũkwenda nĩoke.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("65. Aria Morite Nimagacario (NZK 100)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nAria morite, nimagacario, kũu irĩma-inĩ maracanga\n" +
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
                "\t\t\t\t\t\t\t\t\t\t2.\nArĩa morĩte, nĩmagacario, Macokerio harĩ Jesũ,\n" +
                "Mũmonie njĩra ya ũhonokio, Mone muoyo ũtagathira.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩngũthiĩ tondũ We nĩanjĩtĩte, Ndĩmume thutha hingo ciothe,\n" +
                "Arĩa marakua, na arĩa magwĩte, Moke kwĩ Jesũ nĩwe njĩra.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("66. Njoya, Njoya Mwathani");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNjoya, njoya Mwathani, nduĩke waku, waku ki,\n" +
                "Moko maya ngũkũhe, ũmaathe nginya tene\n" +
                "\n" +
                "ũtherie thĩĩnĩ wa thakame, Theria rũĩ-inĩ rũa thakame,\n" +
                "Njoya Mwathani, na indo ciakwa ciothe Ndũĩke waku waku ki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nOya magũrũ makwa, kũria wandũma ngathiĩ,\n" +
                "Oya mũgambo wakwa, ngocage rĩĩtwa rĩaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nKanua gakwa ngũkũhe, njaragie ndeto ciaku,\n" +
                "Mbeca ciakwa o na indo ngũkũhe ciothe ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nMĩaka yakwa ya muoyo, hinya na ũũgĩ wakwa,\n" +
                "Nĩngwenda wamũkĩra, ndũũre wĩra-inĩ waku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nNĩndĩkwendaga Jesũ, Njoya njoya o rĩu,\n" +
                "Ndũkandege Mwathani, Ndĩ waku kũna kũna.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("67. Nguuka Mutharaba-ini (NZK 115)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\n" +
                "Ngũũka mũtharaba-inĩ, ndĩ kĩonje ndĩ mũthĩni,\n" +
                "Ndige maũndũ mothe, njũke ũnyamũkĩre.\n" +
                "\n" +
                "Ngũkwĩhoka we wiki, we wiki mwana wa Ngai,\n" +
                "Na ngũinamĩrĩre we ũhonokie Jesũ rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\n" +
                " Ngũgũkaira Mũno, tondũ wa ũũru wakwa,\n" +
                "Jesũ nake ekuga, “Ngũgũthambia wothe rĩu.”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\n" +
                " Rĩu ngũkũhe ciothe, matukũ makwa na indo,\n" +
                "Ngoro hamwe na mwĩrĩ, nĩ ciaku nginya tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\n" +
                "Thakame ĩo yaku, ĩngarũrĩte ngoro,\n" +
                "Ngatiga meerirĩria, nũmagĩrĩre Jesũ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("68. Ningwendete Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩngwendete Jesũ, Nĩngwendete ma,\n" +
                "Nĩngwendete Jesũ na Ngai wakwa.\n" +
                "Nĩngwendete mũno, nawe nĩũũĩ,\n" +
                "Nacio ciĩko ciakwa nĩciumbũraga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgenaga na ngoro, na ndigĩtiga,\n" +
                "Nĩ ũtonga njĩrĩirũo ngenda ndĩwone.\n" +
                "Ngaikara na Jesũ, na araika,\n" +
                "Na andũ ngiri nyingĩ arĩa mendaine.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdaikara na Jesũ no ngarathimwo,\n" +
                "No ngona gĩkeno na kĩhurũko,\n" +
                "ũhonokio waku na wendo waku,\n" +
                "Nĩcio ndĩinagĩra na kanua gakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nWe Mũkũũri wakwa, Wĩ Mũthamaki,\n" +
                "Wa salemu njega na ũndeithagia,\n" +
                "Ngũgũkumia na mũgambo wa igũrũ,\n" +
                "Ngũkenera Jesũ rũĩ rũa muoyo.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("69. Nguuka Ndige Nduma Na Kieha");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgũũka ndige nduma na kĩeha, ngũũka gwaku, gwaku Jesu\n" +
                "Unjohore nyone utheri, nguuka gwaku Jesu\n" +
                "Ndĩ muruaru we uhe hinya, ukĩa wakwa nĩukuninĩra,\n" +
                "Ndige mehia njuke harĩ we, nguuka gwaku Jesu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nThoni ciakwa o na ũitangi, nĩngũcite, njũke gwaku\n" +
                "Mũtharaba waku ũndongie, ngũũka gwaku Jesũ.\n" +
                "Ngũtiga kĩeha ũhe thayu, ndige gwĩtanga ũhuurũkie,\n" +
                "Ndige ruo nyinage rũĩmbo, ngũũka gwaku Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdige ngũrũ o na mwĩtĩyo, ngũũka gwaku, gwaku Jesũ\n" +
                "ũnjathage na ũndathime, ngũũka gwaku Jesũ.\n" +
                "Ndige kwĩigania ngwĩhoke, ndige thĩna ndĩkenagĩre,\n" +
                "Nyũmbũkage ngorĩra thĩna, ngũũka gwaku Jesũ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("70. Ningwenga Nduike Mwana Waku");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩngwenda nduĩke Mwana waku, ũndute, ũnyonie,\n" +
                "Njĩkage o ũrĩa wendaga, Ndeithia ndeithia rĩu.\n" +
                "\n" +
                "Ndĩ waku …. (kĩũmbe), Ndĩ waku ….(kĩũmbe)\n" +
                "Ndĩ waku Mwathi Jesũ\n" +
                "Ndĩ waku …. (kĩũmbe), Ndĩ waku ….(kiũmbe)\n" +
                "Ndĩ waku waku kĩũmbe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nĩkeno ciathĩ na ũtonga, ũngĩaga ho Jesũ,\n" +
                "Ngũtiga ciothe ngĩgũcaria, Nĩwe mũtongu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdahutatĩra ikeno cia thĩ, Nguhĩrĩria Jesũ,\n" +
                "Thĩini waku wĩ na indociothe, Na kĩhuurũko.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("71. Ni Mugambo Wa Muriithi (NZK 193)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩ mũgambo wa Mũrĩithi, ũraiguika kũu werũ-inĩ,\n" +
                "Nake areta ng’ondu ciake, Iria ciũrĩire kũraya.\n" +
                "\n" +
                "Rehei rehei, rehei ciume mehia-inĩ,\n" +
                "Rehei, rehei, marehei kwĩ Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNũ ũkwenda gũthiĩ rĩu, Gũteithĩrĩria Mũrĩithi,\n" +
                "Acokie ng’ondu kiugũ-inĩ, Itigakuĩre mehia-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdũkaage gũthikĩrĩria, Mwathani ũcio ũrakwĩra.\n" +
                "“Ta thiĩ ũgacarie ng’ondu, O kũndũ guothe kũrĩa irĩ.”");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("72. Kundu Guothe Migunda-ini");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nKũndũ  guothe mĩgũnda-inĩ ĩiyurĩte magetha\n" +
                "Mĩkuru-inĩ na cianda-inĩ magetha no gũkenga.\n" +
                "\n" +
                "Mwene magetha nĩ wega ũkĩrehe agethi\n" +
                "Monganie magetha maku nĩguo wĩra ũthire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMamatware kwĩ rũci-inĩ o na riũa rĩarĩte\n" +
                "O kinya riũa rĩthũe makĩũngania magetha.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nInyuĩ agethi a Mwathani rehei magetha rĩu,\n" +
                "Ningĩ hwaĩ-inĩ mũtonye gwake mũkenete ma.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("73.  Jesu Riu Niaretana (NZK 107)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ rĩu nĩaretana, “Nũ ũkũndeithia wĩra?”\n" +
                "Magetha rĩu nĩ maingi, Nũ ũgũthiĩ kũũngania,\n" +
                "Igua kayũ nĩaretana, nĩũrĩheo kĩheo,\n" +
                "Nũ ũkũmũcokeria oige, “Mwathani nĩ niĩ ngũthiĩ.”\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nũngĩaga kũringa iria, waaga gũthiĩ kũraya,\n" +
                "Ruta wĩra kũũ kwanyu, kũũ itũũra-inĩ rĩanyu.\n" +
                "Waaga kwaria ta araika, kana hihi Paulo,\n" +
                "Onia andũ wendo wa Jesũ, Meere nĩamakuĩrĩire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nWaaga kũheo gĩtĩyo, ũngĩaga gũtũgĩrio,\n" +
                "Ta andũ arĩa maheeto wĩra, Monagie andũ njĩra;\n" +
                "Wahota kũhoya mũno, na ũtanahe mũno,\n" +
                "Tuĩka ta Harũni mwega, nĩateithĩrĩiria Musa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nRĩrĩa Mwathani aragwĩta andũ no marathira,\n" +
                "Ndũkamũcokerie uuge, “Ndirĩ ũndũ ingihota”\n" +
                "Nyita wĩra na gĩkeno, kenera wĩra wake,\n" +
                "Agwĩta wĩtĩke narua, “Ndĩ haha Jesũ ndũma.”");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("74. Ndiraririkana Bururi Muthaka (NZK 58)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNdĩraririkana bũrũri mũthaka Ngatonya riũa rĩgĩthũa,\n" +
                "Wega wa Jesũ wanginyia kuo\n" +
                "Thũmbĩ yakwa nĩngagemerio na njata?\n" +
                "\n" +
                "Nĩngona, tũcata, Tũcata thũmbi-inĩ yakwa\n" +
                "Narĩo riũa rĩathũa?\n" +
                "Ndeyona gĩkundi-inĩ kĩa arĩa endeku\n" +
                "Nĩngona ng’emeirio na njata?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgũhoya na ndute wĩra wa Mwathani Ndĩmũrehere andũ aingĩ,\n" +
                "Nĩguo ngekĩrĩrũo tũcata thũmbĩ-inĩ ĩhenagie O ta gĩciicio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdamuona nĩngakena mũno Ndamũtwarĩra arĩa nyonetie njĩra,\n" +
                "Nĩguo njĩkĩrũo tũcata thũmbĩ-inĩ Ihenagie o ta gĩciicio.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("75. Inyui Ni Inyui Ene Uthamaki (NZK 76)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nInyuĩ nĩ inyue ene ũthamaki, Mũkomete nginya rĩu nĩkĩ?\n" +
                "Arahũkai mwĩyohe mathaita Na ihenya mũno hingo nĩ thiru.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNdũrĩrĩ cia thĩ iria iina hinya, Irenda mbaara o na wanangi\n" +
                "Thikĩrĩria ũigue makaari macio wetereire kĩ ene ũthamaki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMũtikagonderere o na atĩa Riiri wa thĩ no ta kĩruru\n" +
                "Tua nyororo icio Shaitani akuohete, Wetereire kĩ mwene ũthamaki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nRoria maitho maku mbere wega, Wone Mũthamaki nĩaroka,\n" +
                "Kũrĩa irĩma-inĩ nĩũkuona ũkengi Kena ũthamaki nĩ waku!");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("76. Wiigue Uhoe (NZK 71)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWĩigue ũhoe, Rĩrĩa wĩ na ihinda, Ihinda ti inene, ũkĩra wĩhũge,\n" +
                "Mwĩri ndũrĩ hinya, Thũ iitũ nĩ njamba\n" +
                "Mũhikania nĩegũka, Rĩu e hakuhi.\n" +
                "\n" +
                "Wĩhũge ũhoe, Wĩhũge, ũhoe,\n" +
                "Wĩigue ũhoe mũthenya na ũtukũ\n" +
                "Ndũkae gũcũnga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNina toro waku, Nina mathangania,\n" +
                "Nĩwĩrĩirũo kiheo, Gĩkeno matu-inĩ,\n" +
                "Jesũ nĩeiguire, Agĩkũhoera, Athithinire thakame no ũndũ waku\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nĩtĩkĩra Jesũ, Nowe hinya waku, Wĩyohe mathaita, Thũ ndĩrĩ kũraya Rĩu hena hingo, Tiga gũte ihinda, Ndũkagonderere, ũkĩra wĩhũge. ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("77.  Tutiui thaa (NZK 173)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTũtiũĩ thaa, iria Jesũ arĩũka\n" +
                "Thengia marũũri, macio nomo tũrona,\n" +
                "Nake nĩegũũka, ahingie ciĩranĩro, No thaa tũtoĩ.\n" +
                "\n" +
                "Nĩegũũka nĩtwĩhũge thaa ciothe,\n" +
                "Nĩũgũũka, Haleluia, Haleluia,\n" +
                "Egũũka na matu, na ũkengi wa Ngai,\n" +
                "No thaa tũtoĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMũndũ ũrĩa mũũgĩ, e mũheere wa ũhoro,\n" +
                "Ndũrore Mbuku, ũguũrĩrio maũndũ\n" +
                "ũrathi wothe, githĩ to ũrahinga, No thaa tũtoĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nRĩu nĩtũhoe, na tũgwatie matawa,\n" +
                "Ningĩ twĩhũge tũkĩrutaga wĩra,\n" +
                "Na rĩrĩa arĩũka tũtikorũo nduma-inĩ, no thaa tũtoĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("78. Ngwenda Witikio Murumu (NZK 79)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgwenda wĩtĩkio mũrũmu ũtangĩhooteka,\n" +
                "Wa gũtooria ĩngĩkiara Ndoragie thũ ciothe,\n" +
                "Wa gũtooria ndakĩara\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgwenda wĩtĩkio mũrũmu Na ti wa mateta,\n" +
                "Ndona thĩna ndona ruo Ndĩĩhokage Ngai,\n" +
                "Ndona thĩna na ruo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nũũru woka ndikamake, ũthiũ ũgakenga\n" +
                "Njage guoya njage nganja, ũgwati ũngĩũka,\n" +
                "Njage guoya na nganja.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nMwathani ũhe wĩtĩkio ũcio ndĩrahoya,\n" +
                "Gwoka thĩna ngomĩrĩria, Ngatooria mathĩna,\n" +
                "Nginya ngeyona gwaku.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("79. Aria Atheru Wihugeni (NZK 160)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nArĩa atheru wĩhũgeni, Hinya wa igũrũ ũkĩenyenyio,\n" +
                "Akiai matawa manyu, Mwathani wanyu nĩegũka.\n" +
                "\n" +
                "Nĩegũka, Jesũ wĩtũ, Oke na ũkengi wake,\n" +
                "Ningĩ oke gũthamaka, Nĩegũka, Jesũ witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nCiĩranĩro ciake ciothe, Nĩ wohanĩri wa mehia\n" +
                "Na matonyo megũkenga, Hamwe na thũmbĩ hunjiai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMothamaki nĩ maragwa, naco ngaari ciake ciũke,\n" +
                "Hunjiai tha ciake nyingĩ, Coro wake nĩũkũgamba.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNdũrĩrĩ nĩiranangĩka, Kristo no ateng’erete\n" +
                "Kahinda nĩ ta gathiru, ũthamaki nĩ mũkinyu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nEhia rĩu teng’erai, Kristo amũthaithanĩrĩre,\n" +
                "ĩno nĩyo hingo ya tha, Thutha mabuku mahingwo.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("80. Ngoro Yakwa Wihuge (NZK 67)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nArahũka we ngoro, thũ ngiri na ngiri,\n" +
                "Nĩirageria gũkũgũithia, ũguucio mehia-inĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nArahũka ũhoe, nĩguo ndũkahootwo,\n" +
                "Rũa mbara hĩndi ciothe hoyaga ũteithio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdũrĩ ũrahootana wĩyohe mathaita,\n" +
                "Ndũgatige kũmĩrũa kinya ũkoya thũmbĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("81. Hĩndĩ ĩrĩa Wĩra ũgathira (NZK 179)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nHĩndĩ ĩrĩa wĩra ũgathira, Nao agethi mainũkie,\n" +
                "O mũndũ magetha make kũu Jerusalemu.\n" +
                "\n" +
                "Gĩkeno nĩ gĩkaingĩha, Gĩkeno gĩtagathira,\n" +
                "Gĩkeno kĩa mũthenya ũcio, Agethi mainũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMũthenya ũcio nĩtũkaina, na tũgĩcokagia ngatho\n" +
                "He Kristo mũnene witũ, kũu Jerusalemu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nIkeno nyingĩ nĩ tũkona, Itũũro-inĩ cia matũ-inĩ,\n" +
                "Jesũ nĩwe mũthondeki, kũu Jerusalemu.\n");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("82. Tuhandaga Gwakia (NZK 55)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTũhandaga gwakĩa na mĩthenya yothe,\n" +
                "Nginya o hwaĩ-inĩ mbeũ cia wendo,\n" +
                "Na nĩ twetereire hingo ya kũgetha.\n" +
                "Tũgakenera ma magetha maitũ.\n" +
                "\n" +
                "Magetha maitũ, magetha maitũ,\n" +
                "Tũgakenera ma magetha maitũ.\n" +
                "Magetha maitũ, magetha maitũ,\n" +
                "Tũgakenera ma magetha maitũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nTũhande riũa-inĩ, ona ciĩruru-inĩ,\n" +
                "Heho na rũhuho tũcitorie biũ,\n" +
                "Thutha-inĩ nĩtũrĩnina wĩra witũ,\n" +
                "Tũgakenera ma magetha maitũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nTũhandĩre Mwathani mĩthenya yothe,\n" +
                "Hĩndĩ ya mathĩĩna ona maithori,\n" +
                "Maithori maathira, niagatwamũkĩra,\n" +
                "Tũgakenera ma magetha maitũ. ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("83. Ungihanda Na Maithori");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nũngĩhanda na maithori ũhande na wendo mũingĩ,\n" +
                "Ndũkanoge, ndũgakome, Nĩũgũikũrũkĩrio tha.\n" +
                "\n" +
                "Ta rora wone mĩgũnda, cia Ngai nĩiroma\n" +
                "No agethi meha rĩu magetha nĩmakinyu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nIreera nĩrĩikũrũku Mĩrũri nĩĩmũrĩkĩte,\n" +
                "Maciaro nĩũkuona meho Hingũria maitho wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nHanda na tiga kũnoga Nina guoya wa ngoro,\n" +
                "Ruta wĩra o ta Nyama Na ndũngĩaga magetha.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("84. Muthamaku Niatwitite (NZK 172)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũthamaki nĩatwĩtĩte, tũtonye iruga-inĩ rĩake;\n" +
                "Gũgakĩhana atia hĩndĩ iyo Mũthamaki oka?\n" +
                "\n" +
                "Mũrũ-wa-iya Jesũ oka, gũkahana atia\n" +
                "Tũkahaana atia acoka, wee ona niĩ?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNa nĩagoka e na ũnene, ekĩrwo thũmbi na ti mĩigua,\n" +
                "Titherũ ũnene nĩ ũkonwo Mũthamaki oka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nAkametĩkĩra akenete, arĩa me na ngũo cia ũhiki;\n" +
                "Tũkarathimwo twamũkenia mũthamaki oka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNĩgũkagia na kwamũranio, aria aregi nĩ makarĩra;\n" +
                "ĩo nĩ hĩndĩ ya kĩmako Kristo acooka.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("85. Riria Jesu Agacokereria (NZK 169)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nRĩrĩa Jesũ agacokereria Ndũrĩrĩ mbere yake,\n" +
                "Ciira witũ nĩũgatuo wega Kana tũgatuĩrũo ũũru?\n" +
                "\n" +
                "Nĩagaita ngano yothe ikũmbĩ-inĩ Mahuti magaikio riiko.\n" +
                "Ciira witũ nĩũgatuwo wega Andũ rĩrĩa makariũkio.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩtũkaigua mũgambo wa Jesũ , Twĩtwo ‘ngombo’ njathĩki\n" +
                "Kana nĩ kũinaina tũkainaina Tũthengio gĩtĩ-inĩ gĩake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩagatheka ona ciana ciake Aciona na rũũri rũake,\n" +
                "Iheo ũgemu wa matu-inĩ iturĩtie ndu harĩ we.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNĩ wega twĩhũge twetereire Tũgwatie matawa maitũ,\n" +
                "Mũhikania etana iruga-inĩ Tũtonyanie hamwe nake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nNgoro ciitũ nĩirore matu-inĩ Na tũkirĩrĩrie thĩna,\n" +
                "Na twanina rũgendo o wega Tũgathiĩ hakuhĩ nake.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("86. Magerio Mathira Ndikie Wira");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMagerio mathira ndĩkie wĩra, Ndakinya murĩmo kurĩa kwega,\n" +
                "Njikaranagie na Jesu wakwa Ngamugocaga mĩndĩ na mĩndĩ\n" +
                "\n" +
                "Ndamugoca hĩndĩ ciothe, Hĩndĩ ciothe, hĩndĩ ciothe,\n" +
                "Anjĩtĩkĩra ngamuona uthiu, Ngamugocaga, mĩndĩ na mĩndĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nAngĩkanyonera handu iguru, Nĩgetha ngamuona uthiu wake,\n" +
                "Nĩngakenera wega ucio wake, Ngamugocaga mĩndĩ na mĩndĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nArata akwa arĩa twatiganire Ngamona kuo makenete muno,\n" +
                "Na ndona Mwathani agĩtheka, Ngamugocaga mĩndĩ na mĩndĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("87. Nitukonana Na Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩtũkonana na Jesũ, ũthiũ kwa ũthiũ nĩguo,\n" +
                "Hĩndĩ ĩrĩa akonithanio, Nĩngamuona Mũkũri.\n" +
                "\n" +
                "Nĩtũkonana na maitho, kũu gwitũ matu-inĩ,\n" +
                "Kũu mwago-inĩ wake, nginya tene na tene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRĩu ndĩmuonaga na mairia, ti ũrĩa atariĩ kũna;\n" +
                "No hĩndĩ ĩrĩa agacoka, nĩngamuona wothe biũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMaithori hamwe na thĩna, nĩikaninwo ciothe biũ,\n" +
                "Ithua nĩikarũngario, mũtumumu agĩe maitho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nũthiũ na ũthiũ o kũna, hau gĩkeno gĩtheri,\n" +
                "Ngakena mũno mũno ma, rĩrĩa ngona Mũkũũri");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("88. Twaragia Uhoro Mwega");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTwaragia ũhoro mwega Wa mũciĩ mũthaka mũno,\n" +
                "Riiri wakuo nĩmuumbũre ĩĩ hihi nĩngatonya kuo.\n" +
                "Njĩra ciakuo no thahabu, Thingo cia tũhiga twega\n" +
                "Ikeno ciakuo tũtiũĩ ĩĩ hihi nĩngatonya kuo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nAndũ akuo matiĩhagia Kĩeha gũtirĩ na thĩna\n" +
                "Wa ngoro o na wa mwĩrĩ ĩĩ hihi nĩngatonya kuo.\n" +
                "Wendani nĩ mũingĩ, Namo matonyo nĩ matheru ma,\n" +
                "Nĩguo kanitha wa mbere, ĩĩ hihi nĩngatonya kuo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNjagĩria ngoro ĩthere Hotage magerio mothe\n" +
                "Nĩguo naniĩ ngemenyera Wega ũrĩa o monaga\n" +
                "Ngaceeraga mĩgũnda-inĩ Nyone ũgooci wa Ngai,\n" +
                "Twĩ hamwe na araika ĩĩ hihi nĩngatonya kuo.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("89. Kuu Gutitukaga (NZK 182)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nKũu  gũtitukaga kwĩ na mũciĩ mwega,\n" +
                "Naguo wĩna mĩromo ikũmi na ĩĩrĩ.\n" +
                "\n" +
                "Tũkagirio maithori, gikuũ nĩgĩgathira,\n" +
                "Ruo na guoya nĩikora, Na gũtitukaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMĩrango nĩ ya ruru Nĩ mũciĩ mũthaka,\n" +
                "Njĩra nĩ cia thahabu, Na gũtitukaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMĩrango ndĩhingagwo, Ya gũtonya thĩinĩ,\n" +
                "Kwĩ na rũũĩ rũa muoyo, Na gũtitukaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nKũu gũtirĩ riũa Jesũ nĩwe riũa,\n" +
                "Na nĩ kũndũ gũthaka, Na gũtitukaga.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("90. Murango Ni Muhingure (NZK 110)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũrango nĩ mũhingũre, Nguona ũkengi mwega,\n" +
                "Uumĩte mũtharaba-inĩ, ũkandoria nĩ wendo.\n" +
                "\n" +
                "ũi, ũi tha cia Jesũ!, rĩu ndĩmũhingũrĩre,\n" +
                "Hihi nĩniĩ ma? Hingũrĩirũio ũguo?\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMwendi gũtonya nĩatonye ũhingũrĩtwo wothe,\n" +
                "Mũthĩni o na gĩtonga O na ndũrĩrĩ ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nThũ ciakũnyiira thiĩ mbere Tha ciake nĩigũthira,\n" +
                "Kuua mũtharaba waku, Nginya ũheo thũmbĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nTwakinya kũu mũrĩmo Tũige mĩtharaba thĩ,\n" +
                "Tuoe thũmbĩ tũciĩkĩre Twendage Mũũmbi witũ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("91. Mbaara Ni Njihu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMbaara nĩ njĩhu Thũ ĩĩ hamkuhĩ, Nĩtũrũĩrĩre Jesũ.\n" +
                "Oyai mathaita Na mũũmĩrĩrie, Muuge atĩ Nĩkwandĩkĩtwo.\n" +
                "\n" +
                "Arahũka ũtarĩ na guoya O kahora mwaranagĩrie,\n" +
                "Menereria wĩra wa shaitani, Kristo nĩwe mũnene witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩtũthiĩ mbere tũtekũmaka tondũ nĩithui tũkũhota\n" +
                "Itimũ na ngo nĩ ũhoro wa ma naguo ndũrĩ hingo ukahotwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMwathani mwega tũthikĩrĩrie tũteithie tondũ wĩ mwega,\n" +
                "Mbaara yathira ũtwamũkĩre ũtũhe mũndũ thũmbĩ yake.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("92. Muriithi Mwega Wa Ng’ondu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũrĩithi mwega wa ng’ondu cia Isiraeli,\n" +
                "Amũkĩra ng’ondu ciaku, Na ũcihĩmbĩrie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRekei twana o natuo Tũũke tũrathimwo\n" +
                "Nĩkĩo mwathi wa araika Okire gũkũ thĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nTwakũrehera na ngatho ũciari ũyũ witũ,\n" +
                "Nĩtwakũne amũkĩra. Tondũ tũrĩ aku.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("93. Muumbi Wa Thi O Na Iria");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũũmbi wa thĩ o n oroa Nĩtwamũriruo nyũmba ĩno\n" +
                "Nĩ wĩra wa moko maitũ Twahe Ngai o na Mũrũwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRehe wega waku nyũmba ĩno nĩguo ehia mangĩũngana,\n" +
                "Atheru o na araika, Mainagĩre wendani waku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nArĩa mena kĩeha ngoro-inĩ Mone kĩhuurũkio gĩaku\n" +
                "Mũhonokia ũmohere nao hamwe makumie Ngai.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("94.  We Niwe Wandutiire");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWe nĩwe wandutĩire, Igongona rĩiganu\n" +
                "Nĩwanyamarĩkire Mutĩ-inĩ wa kĩrumi\n" +
                "Ugĩtuonia kwĩnyihia Ugĩthambia nyarĩrĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgoro yakwa wahota kumena uguo ekire?\n" +
                "Nowe ndaigana kwĩhia koruo e haha rĩu\n" +
                "Ndamuthambia nyarĩrĩ Nĩguo ndĩnyihie take.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩ gĩkeno kĩnene Ngĩkuonia wendo wakwa\n" +
                "Nĩ kahinda uheete Na nĩunjuthĩrĩirie\n" +
                "Wone kwĩnyihia gwakwa, Ndurie ndu mbere yaku.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("95. Twatua Kurĩa Giathĩ");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTwatua kũrĩa gĩathĩ rĩu, Kĩa mũgate ona ndibei,\n" +
                "Tuone thakame ĩgĩitika, Ya mũtharaba ĩtũtue aku,\n" +
                "Twarĩrie ngoro-inĩ ciitũ, Tũkene nganja ithire,\n" +
                "Wĩ hakuhĩ na tũtione, No mũgambo tũkũigua rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nTũganĩre wega o rĩu Tũigue ũrĩa woragirũo,\n" +
                "Tũguucie ngoro ici ciitũ, tuone riiri ũcio waku,\n" +
                "Tũkinye o hakuhĩ nawe hau ũroira thakame,\n" +
                "Nĩguo tuone wĩyendi witũ, Twegerekania nawe rĩu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nTwatue kuuma metha-inĩ, ĩno ũtwarĩire Jesũ,\n" +
                "Reke tĩigue wega ngoro, Tukũre tũhane tawe,\n" +
                "Twakũrora na twakuona, Tũhananage nawe,\n" +
                "We Mwathani, Mũrutani, Reke tũgwathĩkĩre.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("96. Mucii Wi Na Wendani (NZK 184)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMucii wina wendani ni mwega muno,\n" +
                "Gutari matetania ni kwega muno,\n" +
                "Buthi hamwe na thayu irehagwo ni wendo\n" +
                "Matuku magathira kwi na gikeno.\n" +
                "\n" +
                "Kwendana, ni hitho, Ya kuninaga thina, Hingo ya muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMucii niurigemaga kwagia wendani,\n" +
                "Rumena o na uiru ciagithio mweke.\n" +
                "Mucii niugemio wega uhane ta mahua,\n" +
                "Matuku mathirage kwi na gikeno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nAria othe me iguru nimakenaga,\n" +
                "Mona mucii wendaine, wi na gikeno,\n" +
                "Thi yothe niikenaga micii yagia wendani,\n" +
                "Muumbi witu o nake no akenaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nJesu utwamukire tuhe wendani,\n" +
                "We niwe igongona, tuhe wendani,\n" +
                "Tugitire mogwati, na tutige kwihagia\n" +
                "Utuiguire tha Jesu, tuhe wendani.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("97. Wendo Niguo Gikeno (Nzk 199)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWendo nĩgũo gĩkeno, wagagĩria njĩra\n" +
                "ũtũteithagia gũtuga andũ hingo ciothe\n" +
                "\n" +
                "Ngai nĩ wendo twĩ ciana ciake,\n" +
                "Nĩ wendo, tũtuĩke take,\n" +
                "Wendo nĩguo gĩkeno, wagagĩria njĩra,\n" +
                "ũtũteithagia gũtuga andũ hingo ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nThĩ ĩiyũirwo nĩ kĩeha, mĩrĩmũ na gĩkuũ,\n" +
                "Twĩ na wendo nĩtũgerie kũguucia andũ arĩa angĩ.\n" +
                "\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMuoyo ũyũ wathira, twambatio matuinĩ,\n" +
                "Tũkaina mĩndĩ na mĩndĩ, wendo wake Jesũ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("98. Iguai Uhoro Wa Mwathani (NZK 95)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nIguai ũhoro, wa Mwathani witũ, Mũrehe gĩcunjĩ mũthithũ-inĩ,\n" +
                "Mũrehe na ngoro cianyu ciothe, Nĩguo kĩrathimo gĩũke.\n" +
                "\n" +
                "Rehei gĩcunjĩ mũthithũ-inĩ, Na mũkĩngerie rĩu nakio;\n" +
                "Na nĩngũmũhe kĩrathimo,\n" +
                "Nginya mwage gwa kũiga nĩ kũingĩha.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩũkwenda kũheo Roho Mũtheru? No kĩrehe gĩcunjĩ ikũmbĩ- inĩ,\n" +
                "ũikare hakuhĩ na Ngai waku, Nĩguo ũrĩrathimagwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nũrĩ na ihĩtia na Mwathani waku? ũkĩrehe gĩcunjĩ ikũmbĩ-inĩ,\n" +
                "ũrehe gĩcunjĩ mũthithũ-inĩ, nĩguo ũrĩrathimagwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nGocaga Mwathanĩ na ngoro yothe, ũkĩrehe gĩcunjĩ ikũmbĩ-inĩ,\n" +
                "Wĩtĩkie ciĩranĩro ciake ciothe, Nĩguo ũrĩrathimagwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nTũine ithuothe nyĩmbo cia gĩkeno, Tukĩrehe gĩcunjĩ ikũmbĩ-inĩ,\n" +
                "Tũine na gĩkeno kĩnene mũno, Tondũ nĩ tũkũrathimwo.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("99.  Wira Wakwa Wathira (NZK 177)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nWĩra wakwa wathira, ndarĩkia kũhonokio\n" +
                "Na heo mwĩrĩ ũrĩa ũtagakua,\n" +
                "Nĩngamenya Mũkũũri ndakinya kũu igũrũ,\n" +
                "Agatuĩka wa mbere kũndangĩra.\n" +
                "\n" +
                "Kũmenya nĩ ngamũmenya, ahonokia tũikarage nake,\n" +
                "Kũmenya nĩ ngamũmenya, ndona irema cia mĩcumarĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩngagĩa na gĩkeno ndamuona ũthiũ wake,\n" +
                "Onaguo ũkengi wa maitho make,\n" +
                "Nĩngakumia Mũkũũri tondũ wa tha na wendo,\n" +
                "Icio iheete handũ kũu igũrũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩngatonya kĩhingo ndĩ na nguo igũkenga,\n" +
                "Anginyie kũu gũtarĩ maithori,\n" +
                "Na nĩngaina rũĩmbo rũa tene na tene kũna,\n" +
                "No nĩngenda nyone Mũkũũri mbere.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("100. Huuruka Wega");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nHuurũka wega, Koma ũguo endete,\n" +
                "Ndahĩtagia, Nĩakũmenyerere wega,\n" +
                "Toro mwega Ndũcoka kuona ũũru\n" +
                "Maithori nĩ makĩ Ngai endete, Koma wega\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nWĩra waku rĩu O na kũgetha gwaku\n" +
                "Nĩwarĩkia ndũcoka kũrĩra rĩngĩ,\n" +
                "Ndũrĩ na thũ Ndũcoka kuona thĩna,\n" +
                "Koma wega ndũcoka kunoga, koma wega.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nHurũka wega, Rĩu tũtikũgeithia,\n" +
                "Kinya oige, Tũgacemania igũrũ\n" +
                "Tũkũgeithie, na tũtigakua rĩngĩ,\n" +
                "Twacemania Maithori tũtikona, koma wega.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("101. Tubatithie (NZK 189)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTũbatithie na Hinya wa Igũrũ, \n" +
                "ũtwerũhie na wendo waku Jesũ.\n" +
                "\n" +
                "Twagũthaitha, nĩtwakũhoya Jesũ,\n" +
                "ũtũbatithie na Wendo na Roho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nTũtiagĩrĩire, Tũtirĩ atheru.\n" +
                " Tũthambie tũtherie mehia mathire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNawe Ndũtũra ya matu-inĩ.\n" +
                " Twakũhoya ũũke ũtũrathime.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nTũigue mũgambo, uumĩte igũrũ,\n" +
                "Uuge, “Wĩ mwana wakwa nyendete ma.”");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("102. Ngai Niagoocwo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgai nĩagoocwo nĩ ciũmbe ciothe\n" +
                "Wendo wake nĩwatũheire Jesũ\n" +
                "ũrĩa werutire atũhonokie\n" +
                "Tũgĩe na muoyo wa tene na tene\n" +
                "\n" +
                "Mũkumie mũkumie andũ a thĩ maigue\n" +
                "Mũkũmie mũkũmie andũ othe kenai\n" +
                "ũkani kwĩ Jesũ mwana wa Ngai.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nũhonokio wa Ngai nĩguo kĩheo giitũ\n" +
                "Ngai nĩatwĩtĩte twĩ andũ a thĩ\n" +
                "Arĩa meetĩkagia na kũmumbũra\n" +
                "Acio nĩmohagĩrwo mehia mao.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩatũreheire ũhoro mwega\n" +
                "Agĩthagathaga gũtũhonokia\n" +
                "No makeria mũno ũndũ mũnene\n" +
                "Jesũ nĩagoka na ithue tũmuone.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("103. Ngucaria Jesu O Riu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgũcaria Jesũ O rĩu, Tũtindanagie nake,\n" +
                "Nũmagĩrĩre makinya, Make njĩra-inĩ njeke.\n" +
                "\n" +
                "Nĩanyendete, nĩanyendete, Nĩnjũi Nĩanyendete,\n" +
                "Nĩanyendete, Akĩnguĩra, Na nĩkĩo Ndĩmwendete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNdĩthiaga kũrĩa enda, ũnyinyi ndũngĩngiria\n" +
                "Njĩra yothe ekũgera, ũnyinyi ndũndigithia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nArũngĩi o mũromo-inĩ Ndonye nyume mehia-inĩ.\n" +
                "Ningaringe ndonye thĩinĩ anjigua anjiyũkie.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("104. Muthamaki Munyumbi.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũthamaki mũnyũmbi, mwene indo ciothe\n" +
                "Nĩũndũ wa ũtugi waku,nĩũndathimaga\n" +
                "Nĩũndũ wa ũtugi waku,nĩũndathimaga.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nWee wanyũmbire nowe ndĩhokate\n" +
                "Njagĩrĩirũo nĩgumagie wega waku mũingi\n" +
                "Njagĩrĩrũo nĩ gũkumagie waku mũingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩkĩ ingiruta na ciothe nĩ ciaku\n" +
                "Wee wendaga o tũgũcokagĩrie ngatho\n" +
                "Wee wendaga o tũgũcokagĩrie ngatho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nũhe wega waku nĩguo ngĩe na hinya\n" +
                "Wa  kũhotithagia nĩguo ndũre nawe Ngai\n" +
                "Wa  kũhotithagia nĩguo ndũrie nawe Ngai. ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("105. Mutharaba-ini wa Mwathani.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũtharaba-inĩ wa Mwathani nĩho ndahoire ũhonokio\n" +
                "Ngoro yakwa nĩyatheririo Jesũ nĩ agoocwo\n" +
                "\n" +
                "Jesũ nĩagoocwo Jesũ nĩagoocwo\n" +
                "Nĩangũũrire na thakame Jesũ nĩgoocwo\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nHau matu-inĩ ũcio wa thĩĩna nĩho ndahoire ũthingu \n" +
                "Nĩaangũũrire na thakame, Jesũ nĩagoocwo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩndahonokirio nĩ Jesũ, rĩu aikaraga ngoro-inĩ\n" +
                "Nĩangũrire mũtharaba-inĩ, Jesũ nĩagocowo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nThakame ya Jesũ ya goro, nĩyo ĩndutaga meehia-inĩ\n" +
                "ĩndwaraga ũhonokio-inĩ Jesũ nĩagoocwo.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("106. Jesu Ningwedete.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ nĩngwendete wee ũtoonga wakwa\n" +
                "Ikeeno cia mehia nĩihutatĩire\n" +
                "Nĩ Mwathani Jesũ wahonokirie \n" +
                "Rĩu nĩngwendeete makĩria mũno\n" +
                ".\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgoro ĩiyũrĩte wendo mũnene\n" +
                "Tondũ nĩwaambire kũũnyenda mbere\n" +
                "ũkĩndutĩra muoyo waku we mwene\n" +
                "Rĩu nĩngwendeete….makĩria mũno.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nHĩndĩ ĩrĩa waambirwo mũtharaba-inĩ\n" +
                "Nĩgetha twoherwo tume mehia-inĩ\n" +
                "Na nĩ wekĩrirwo thũũmbĩ ya mĩigua\n" +
                "Rĩu nĩngwendeete….makĩria mũno\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nBũrũri mũthaka na itũũro njega\n" +
                "Rĩrĩa ngaciĩrorera kũrĩa igũrũ\n" +
                "Ngaaria ndĩ na thũmbĩ ĩrĩa ngekĩra\n" +
                "Rĩu nĩngwendeete…..makĩria mũno.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("107. Ihiga Ria Tene Ma.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nIhiga rĩa tene ma,nĩndĩhithe harĩ we\n" +
                "Tũma maaĩ na thakame,kuma mbaru-inĩ ciaku\n" +
                "Inine wĩhia wothe,ningĩ na hinya waguo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMawĩra makwa mothe,matingĩhiingia watho\n" +
                "Kĩyo gĩakwa kĩnene,kĩeha o na maithori\n" +
                "Itingĩnina wĩhia nowe wiki ũngĩniina\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdirĩ na ihaki niĩ ,ngwĩhoka o mũtharaba\n" +
                "Ndĩ njaga humba nguo,ndĩ mũhũthũ ndeithagia\n" +
                "Ndĩ o mwĩhia no ngũka ,ũthambie itanakua.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nHingo ya muoyo wakwa,ngũtũra ngwĩhokete\n" +
                "Ndakoma mbĩrĩra-inĩ nĩ njũĩ nĩũkandiũkĩa\n" +
                "Njũke gwaku igũrũ,ndũũre ũnene-inĩ wake.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("108. Mutihonokei Aria Mekuura.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũtihonokei arĩa mekũũra \n" +
                "Arĩa megũkua tondũ wa meehia\n" +
                "Na arĩa mekũgwa mũtikĩmooe\n" +
                "Mũmeere wega ũhoro wa Ngai\n" +
                "\n" +
                "Mũtihonokiei arĩa mekũũra \n" +
                "Mũhonokia no ametereire.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nO na maamũrega no ametereire\n" +
                "Endaga mũno mooke meerire\n" +
                "Nĩtũmathaithe ma na ũhooreri\n" +
                "Maamwĩtĩkia nĩekũmoohera.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNgoro-inĩ cia andũ,ũcio shaitani\n" +
                "Nĩarehaga thĩĩna na kĩeha\n" +
                "No wega wa Jesũ nĩmuohanĩri\n" +
                "Akaamahonia na amahonokie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nTũthiĩ tũkahonokie arĩa morĩte\n" +
                "Wĩra wa Ngai wĩna kĩheeo\n" +
                "Tũmaguucie wega na ũhooreri\n" +
                "Matwarĩrwo Jesũ amahonokie. ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("109. Andu Rutaai Wira.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nAndũ rutaai wĩra,rĩu gũtanatuka\n" +
                "Mũikare mwĩhũũgĩte mũkirĩrĩirie\n" +
                "Tungatĩrai Jesũ na horo ũcio mwega\n" +
                "Huunjiai kũndũ guothe andũ othe maigue.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nAndũ rutai wĩra rĩu nĩ kũraatuka\n" +
                "Andũ aingĩ kũu kwanyu me nduma-inĩ\n" +
                "Mũtikareke moore ihinda no rĩrĩ\n" +
                "Jesũ no e gũcooka twarĩĩkia wĩra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nAndũ rutai wĩra gũkirie gũtuka\n" +
                "Andũ othe kũu kwanyu Ngai nĩ ameenda\n" +
                "Ithue tũmũmenyeete nĩtũmahuunjĩrie \n" +
                "Nĩguo Jesũ acooke tũkaamũkenia. ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("110. Nituthiini Ita Thigari Cia Ngai.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩtũthiĩni ita thigari cia Ngai,Jesũ arĩ mbere nĩegũtũtwara\n" +
                "Jesũ nĩarĩkĩtie gũthiĩ mbara-inĩ nĩtũthiĩ kwĩ Jesũ nĩũndũ nĩ wa ma.\n" +
                "\n" +
                "Nĩtũthiĩni ita andũ a Ngai\n" +
                "Nĩtũrũmĩrĩre, Jesũ arĩ mbere.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMbũtũ cia shaitani, hĩndĩ ĩrĩa ĩkaigua,rĩĩtwa rĩu rĩa Jesũ nĩ kũũra ikoora Kenai andũ a Ngai ugĩrĩriai mugĩrĩrie na kayũ na ningĩ na hinya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nGakundi kanini ka andũ a Ngai,ka maithe maitũ igai nĩ riitũ\n" +
                "Tũrĩ rũmwe nao wĩtĩkio nĩ ũmwe,kĩrĩgĩrĩro o kĩmwe ona mwĩhoko.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nMũtiarahũkei andũ a Ngai mũkumiei na ngoro ona mĩgambo\n" +
                "Mũthamaki Jesũ nĩatĩĩo mũno arokumio igũrũ ona thĩ guothe.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("111. Ruagirira Ngai.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nRũagĩrĩra Ngai gũũkũ thĩ ona thĩĩna ũngĩingĩha mũno\n" +
                "Rũaro-inĩ nĩho  ũrĩĩthiaga mehia mangĩritũha.\n" +
                "\n" +
                "Nĩtũrũgame wega igũrũ rĩa rwaro,Rwaro nĩ Kristo wiki\n" +
                "Nĩho he thayũ tũngĩrũũgame gĩtĩ-inĩ gĩake kĩa ũnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRũagĩrĩra o ũhoro wa ma wĩna ngoro theru na wĩtĩkio\n" +
                "Rũaro-inĩ nĩho ũngĩhotera wonje waku mũnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nRũagĩrĩra ũhoro wa ma,ona waikara nĩũkahootana\n" +
                "Rũaro-inĩ niho ũgaakenera mbara ĩno yathira.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("112. Ningwihoka Thakame Iyo.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩngwĩhoka thakame ĩyo ya Mwathani wakwa Jesũ\n" +
                "ũthingu ndirĩ mũiganu wa Gũthambia mehia makwa.\n" +
                "\n" +
                "Ngwĩhanda igũrũ ria Jesũ\n" +
                "Nĩwe rwaro irahĩro\n" +
                "Nĩwe rwaro irahĩro.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nOna rũgendo rwa raiha nowe Mũhonokia wakwa\n" +
                "Ona ndaikanio nĩ ndiihũ ngũnyitwo nĩ hinya wake\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nJesũ nĩwe kĩgoongona ngwihoka thakame yake \n" +
                "Ndanyiihĩrwo nĩ indo cia thĩ Mwathani nĩanjiganĩte.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNdanyiitwo nĩguo njiirithio, ndĩna thaayũ ngoro-inĩ\n" +
                "Ndahuumbwo ũthingu wa Jesũ, ndikamaka ndĩ harĩ we. ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("113. Mugambo Wakwa Rucini.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũgambo wakwa rũcinĩ nĩũrĩkayagĩra\n" +
                "Mwathani wakwa ngĩhooya nĩguo andaathime.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgũkũhoya Roho waku nĩguo andoongorie\n" +
                "Njĩkage ũrĩa njagĩrĩirwo, na ũrĩa wendaga\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nArĩa makwĩhokete ma, wee nĩũmarĩithagia \n" +
                "Tondũ nĩwe mehokete, nĩmahingagĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nTondũ wa tha ciaku Ngai, nĩngoka harĩ we\n" +
                "Nĩ ngatũũra ngũinagĩra na ngĩkũhoyaga.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("114. Mwathani Wakwa Unjikarie.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMwathani wakwa ũnjikarie tondũ rĩu gukirie gũtuka \n" +
                "Nĩmbatairio nĩ ũteithio waku tondũ rĩu ndĩ nyiki ũnjikarie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMatukũ maitũ nĩ mathiraga tondũ ũcio gũtirĩ gĩkeno\n" +
                "Maũndũ mothe nĩ mathiraga,we mũtũũra muoyo ũnjikarie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩmbatairio nĩwe hiingo ciothe gũtirĩ ũngĩ ũnjiganĩte\n" +
                "Nũũ ũngĩhota kũndongooria, we Mwathani wakwa ũndoongorie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nGũtirĩ ũgwati ingĩtigĩra omarĩa mothe marĩngoraga,\n" +
                " O na kĩo gĩkuũ ndingĩtigĩra nĩũndoranĩirie ũnjikarie\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nRĩrĩa ngomete tũrĩ onawe, ũtheri waku ũmũrikiire\n" +
                "ũtheri wa igũrũ ndũnyihiire matukũ-ini makwa ũnjikarie. ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("115. Nguthambio Mehia Nikii.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgũthambio mehia nĩkĩĩ,nĩ thakame yake Jesũ\n" +
                "Ngai nĩakenaga nĩ Thakame yake Jesũ\n" +
                "\n" +
                "Gũtirĩ mũthaiga ũngĩthambia mehia\n" +
                "Kana ũngĩtheria, no thakame yake Jesũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nGũtirĩ kĩngĩtheria no thakame yake Jesũ\n" +
                "  Kana kũniina mehia, no thakame yake Jesũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nGũtirĩ mũiguithania no thakame yake Jesũ\n" +
                "Nĩngũmakio nĩ ciira ingĩaga Mwathani Jesũ \n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nHatirĩ mwĩhoko no thakame yake Jesũ\n" +
                "Gũtingĩoneka thayũ no thakame yake Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nThĩ yothe nĩĩhotetwo nĩ thakame yake Jesũ\n" +
                "Nĩtũgakinyio igũrũ, nĩ thakame yake Jesũ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("116. Murigiti Munene.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũrigiti mũnene ma nĩwe Jesũ mũigũa tha\n" +
                "Ona nĩatũhoreragia, Jesũ mũtũhonokia.\n" +
                "\n" +
                "Araika gocai Jesũ mũhonokia.\n" +
                "Gũtirĩ rĩĩtwa rĩega gũkĩra rĩake Jesũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩtũkumie gatũrũme,na nĩwe mũhonokia,\n" +
                "Mehia mothe na mahĩtia,Jesũ nĩamatheragia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNgumo ciothe na rũgooco,ciagĩrĩire Mwathani,\n" +
                "Rĩtwa rĩake nĩrĩega ma,norĩo rĩtũkenagia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nHĩndĩ ĩrĩa agacoka thĩ, na riiri wake mũingĩ\n" +
                "Nĩtũgakena mũno ma,tũgĩtũũrania gwake.   ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("117. Jesu Niwe Munyeendi.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ nĩwe mũnyendi,na nĩnjũĩ nĩguo ki!\n" +
                "Mbuku ya Ngai yugaga,nĩwe mwendi wa twana.\n" +
                "\n" +
                "ĩĩ nĩanyendete ,Jesũ we mwene;\n" +
                "ĩĩ nĩanyendete,nĩnjuĩ nĩguo ma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nJesũ nĩwe mũnyeendi ,nĩanguĩrĩire mũtĩ-inĩ\n" +
                "Wĩhia wakwa anine,Mwana wake ndakore.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nJesũ nĩwe mũnyeendi ,rĩrĩa ndĩhatĩka-inĩ\n" +
                "Ekũu matu-inĩ no anjũthagĩrĩria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nJesũ nĩwe mũnyeendi ,tũtigatigana ni;\n" +
                "Tũrĩthiaga nake,O kinya mũciĩ gwake.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("118. Ninjangite O Kuraya.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩnjangĩte o kũraya,nĩ ngwĩrira rĩu;\n" +
                "Ndigane na mawaganu,nĩngũinũka.\n" +
                "\n" +
                "Nĩngũka harĩ we,ndige gũcanga\n" +
                "Ndagũthaitha ũnjiyũkie,nĩngũinũka (Ngai).\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩ ndete mĩaka mĩingĩ,nĩngwĩrira rĩu;\n" +
                "Nĩ ngwĩrira itarĩ nganja,nĩngũinũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩ nogete nĩ gwĩthũkia,nĩngwĩrira rĩu;\n" +
                "Nĩ ngwĩtĩkia ndeto ciaku,nĩngũinũka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nWee nĩwe mwĩhoko wakwa,nĩngũcooka rĩu;\n" +
                "Nĩ ũndũ nĩwe wanguĩrĩire, Jesũ nĩndooka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nNdĩmũigane nĩ thakame, Yaku Jesũ rĩu;\n" +
                "Thambia there kũna kũna, Jesũ nĩndooka.  ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("119. Jesu Ni Aroiga.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ nĩ aroiga, “we ndũrĩ na hinya;\n" +
                "Wĩhũũge ũhoe, ũka mwana wakwa.\n" +
                "\n" +
                "Thiirĩ  wa mehia ,we nĩandĩhĩire\n" +
                "Hau Mũtharaba-inĩ,na akĩnjohere.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nJesũ nĩnyoonete,ũhoti ũcio waku;\n" +
                "Wahota gũtheria,ngoro ĩna gĩko.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNdĩngĩkĩrandũra ,wega waku mũingĩ;\n" +
                "Rĩu nĩ ngwĩtheria  ,na thakame yaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nRĩrĩa ngarũũgama ,O kũu matu-inĩ\n" +
                "Thũũmbĩ nĩngamĩiga magũrũ-inĩ ma Jesũ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("120. Tuura Wi Mutheru.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTũũra wĩ mũtheru ohĩndĩ ciothe;\n" +
                "Tũmaga ũrata na arĩa etĩkia;\n" +
                "Waragie na Jesũ ohingo ciothe;\n" +
                "ũhoe irathimo mahinda mothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nTũũra wĩ Mũtheru o hĩndĩ ciothe;\n" +
                "Tũũra mahoya-inĩ o hingo ciothe.\n" +
                "ũngĩcũthĩrĩria Jesũ Mwathani;\n" +
                "Nĩũkũgarũrũka ũhane take.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nTũũra wĩ mũtheru o hĩndĩ ciothe;\n" +
                "Mũtongoria waku atuĩke Jesũ; \n" +
                "Hĩndĩ ya gĩkeno ona kĩeha;\n" +
                "Rũmĩrĩra Jesũ nĩ mũhonokia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nTũũra wĩ mũtheru o hĩndĩ ciothe;\n" +
                "Wathagwo nĩ Roho ngoro-inĩ yaku;\n" +
                "Akuonagĩrĩrie njĩra ya ũthingu;\n" +
                "Nĩagakũhaarĩria wĩra-inĩ wa Ngai.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("121. Hindi Njega Ya Mahoya.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nHĩndĩ njega ya mahoya rĩu nĩtwoka harĩ we;\n" +
                "ũtũehererie mathĩĩna,o na mĩnyamaaro yothe\n" +
                "Rĩrĩa gwĩ kĩeha na ruo,nĩũhonagia ngoro ciitũ;\n" +
                "Nĩtũhootaga mathĩna ,hĩndĩ njega ya mahoya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nHĩndĩ njega ya mahoya,tũtware mathĩĩna maitũ\n" +
                "Kũrĩ ũrĩa weranĩirie gũtũteithia arĩa mabataire;\n" +
                "Atwĩraga tũthiĩ gwake na twĩhoke ciugo ciake;\n" +
                "Kwoguo tũmwĩrekererie hĩndĩ njega ya mahoya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nHĩndĩ njega ya mahoya,nĩtũrĩkĩragĩrĩria;\n" +
                "Kwĩhitha kiugo-inĩ kĩa Ngai nginya tũnine rũgendo\n" +
                "Jesũ nĩarĩtũiguaga twamũcaria hingo ciothe;\n" +
                "Nĩtũrĩonanaga nake hĩndĩ njega ya mahoya.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nHĩndĩ njega ya mahoya,nĩnyonaga ũhoreri\n" +
                "Ndĩ kĩrĩma-inĩ igũrũ nyonaga mũciĩ wa Ngai\n" +
                "Ngakena atĩa ndakinya kuo heo mwĩrĩ ũngĩ na thũmbĩ;\n" +
                "Na ngakena hingo ciothe ngĩgocaga mũthamaki.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("122. Hari Jesu Ngwineana.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nHarĩ Jesũ nwĩneana ,ngoro yakwa o na indo\n" +
                "Nĩngũcĩrĩirio nĩ wendo , nĩkĩo ngwĩneana biũ.\n" +
                "\n" +
                "Ngwĩneana biũ, ngwĩneana biũ;\n" +
                "Nĩngũcĩrĩrio nĩ wendo ngwĩneana biũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nHarĩ Jesũ ngwĩneana ,ndĩmũinamagĩrĩre;\n" +
                "Nĩndigĩte ikeno ciothe Jesũ ũnyamũkĩre!\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nHarĩ Jesũ ngwĩneana ,ngarũra nduĩke waku;\n" +
                "Roho amenyagĩrĩre matukũ makwa mothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNdĩheanĩte kwĩ Jesũ na rĩu nĩ nyonete\n" +
                "Gĩkeno kĩa ũhoonokia ngũgooca rĩĩtwa rĩake");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("123. Riria Jesu Agacoka Agire Managi");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nRĩrĩa Jesũ agacoka agĩre managĩ\n" +
                "Na managĩ nĩmo andũ arĩa anakũũra\n" +
                "\n" +
                "Magathera ta njata, ota ya kĩwariĩ\n" +
                "Noguo magathakara managĩ make.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nAkongania akongania, amatware gwake;\n" +
                "Arĩa ega na atheru arĩa anakũũra.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nTwana tũtũ,twana tũtũ mwendete mũkũũri\n" +
                "Nĩ inyuĩ mwĩ managĩ,managĩ make.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("124. Hakuhi Nawe Ngai.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nHakuhĩ nawe Ngai niguo ngwenda , hakuhĩ makĩria Ngai wakwa; Hingo yakwa yothe njikarage nawe ,hakuhĩ makĩria Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNiĩ ndĩ mũgendi rũgendo-inĩ ,ndirĩ ndahurũka ngoro-inĩ\n" +
                "No rwĩmbo nyinaga ũguo ngĩthiaga , hakuhĩ makĩria Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMaũndũ matheru mangucagia , nĩmo maku mega ma igũrũ;\n" +
                "Hĩndĩ ĩrĩa ndĩ toro ũnjikaragie, hakuhĩ makĩria Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nRĩu ngũkũgooca na hinya ma wee ndũũmba ya ihiga rĩa ũhonokio Rĩrĩa ndĩna thĩĩna ndĩũragĩra gwaku , Hakuhĩ makĩria Ngai wakwa.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nRũgendo rwathira rwa gũkũ thĩ ,ũkandwara igũrũ kũu gwaku\n" +
                "Nĩkuo ngakenera tondũ ngatũũraga,hakuhĩ makĩria Ngai wakwa.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("125. Hatigaire Haigana Atia.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nHatigairie haigana atĩa ,kinya mũciĩ, ngĩũria ngĩĩrwo\n" +
                "ũtukũ nĩũrahĩtũka na gũcoke gũkĩe.\n" +
                "Kĩgirĩko rũmia njĩra njata nĩĩgũgutongoria;\n" +
                "Kinya ithirĩro rĩkinye tũthiĩ gwa ithe witũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNgĩũria thĩ, ĩrĩa na riũa igĩcokia mũgambo ũmwe\n" +
                "Rĩu ihinda nĩ rĩthiru ,mũthenya nĩ mũũku;\n" +
                "Kĩgirĩko no wĩhũge, marũũri no maratwonia\n" +
                "Thĩ yothe rĩu yetereire cooro wake wiki;\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNgĩũria njaamba ĩrĩ werũ-inĩ , rũĩmbo rũgĩkĩmĩguucia \n" +
                "ũmĩrĩria ũhotane mbara ti ndaaya rĩu;\n" +
                "Kĩgirĩko ũmĩrĩria ũguucanie ũhootane ;\n" +
                "Kĩheo kĩrĩ ho kũna , Ombara ya thira.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nRĩu mũciĩ ti kũraihu , mũgendi gĩkũngũĩre;\n" +
                "Kĩhonia rĩu nĩkĩo gĩkĩ kĩniarie maithori;  \n" +
                "Kĩgirĩko tũcemanie, kũrĩa gũtagirĩkagwo;\n" +
                "Tiga gĩkeno gĩtheri , mũciĩ gwa Ithe witũ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("126. Nimbatairio Niwe.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩmbatario nĩwe , o hingo ciothe;\n" +
                "Nandirĩ na ũngĩ mwagĩrĩru;\n" +
                "\n" +
                "Jesũ nĩngũbataire, mũno hingo ciothe;\n" +
                "Kĩhotithie mũkũũri nĩnjũkĩte.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩmbatairio nĩwe , ũnjikarie;\n" +
                "Magerio no tũhũ matingĩndooria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩmbatairio nĩwe ũndũ-inĩ wothe;\n" +
                "ũtũũro no tũhũ wa kũraihu.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNĩmbatairio nĩwe , ũndungate;\n" +
                "Ciĩranĩro ciaku ndĩcihingie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nNĩmbatairio nĩwe ,hoti ya mothe;\n" +
                "Ndĩ waku waku ki , matukũ mothe.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("127. Ni uhoro Mwega Ma.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩ ũhoro mwega ma rĩrĩa tũkũũigua ithuĩ agendi gũkũ thĩ;\n" +
                "Mĩtũkĩ Mũthamaki nĩegũka rĩngĩ,naguo ũthamaki ũke.\n" +
                "\n" +
                "Nĩegũka, oke mĩtũkĩ mũno ma,nĩegũka gũkũ thĩ ĩno;\n" +
                "Aiyĩre andũ ake matwaro mũciĩ rĩrĩa agoka Mũthamaki.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMbĩrĩra iria arĩa atheru makomete,ikahingũrwo mariũkio;\n" +
                "Andũ ngiri nyingĩ arĩa makomeete nĩ magacokio muoyo rĩngĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nTũtikamũkana rĩngĩ tũrĩ nao,nĩtũkaina nyĩnbo njega;\n" +
                "Magacemanagia rũũrĩrĩ o rũũrĩrĩ , magũrũ-inĩ ma Gatũrũme.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nHaleluyah! Amen Haleluyah o rĩngĩ , wendo wake nĩ mũnene;\n" +
                "Tũkamũgocaga tene ona tene , tũgegeete nĩgũtũkũũra.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("128. Coro Wa Ngai Ukahuhwo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nCoro wa Ngai ũkahuhwo, Mũthenya wa ithirĩro\n" +
                "Atheru a Ngai nĩ makonganio\n" +
                "Marĩtwa magĩtaanwo niĩ ngakorwo nĩho ndĩ\n" +
                "Nĩguo tũgatwarwo igũrũ kwa Jesũ \n" +
                "\n" +
                "Marĩtwa nĩ magetanwo!\n" +
                "Marĩtwa nĩ magetanwo!\n" +
                "Marĩtwa nĩ magetanwo!\n" +
                "Marĩtwa magĩtanwo ngakorwo ho\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMũthenya ũcio wĩna riiri, rĩrĩa akuũ magatirikio\n" +
                "Nĩmakoima mbĩrĩra-inĩ cia\n" +
                "Andũ ake arĩa atheru amainũkie igũrũ\n" +
                "Marĩtwa magĩtanwo ngakorwo ho\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩtũrute wĩra wake twĩna kĩo kĩnene\n" +
                "Tũheana ũhoro ũcio wake \n" +
                "Na twarĩkia wĩra wake narĩo ihinda rĩthire\n" +
                "Marĩtwa magĩtanwo ngakorwo ho");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("129. Murimo Uria Wa Jordani");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũrĩmo ũrĩa wa Jordani , nĩkuo ndĩroreire\n" +
                "Bũrũri mwega Kaanani, nĩkuo njerekeire\n" +
                "\n" +
                "Tũgatũraga hamwe na Jesũ\n" +
                "Bũrũri ũcio mwega wa gĩkeno\n" +
                "Nĩtũkaina rwĩmbo rwa Musa na Jesũ\n" +
                "Tũkaina tene na tene\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nBũrũri ũcio wĩna ũtheri, ũtheri ũtagathira \n" +
                "Kristo nĩwe riũa riakuo nĩaingatĩte nduma\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nHihi nĩrĩ ngakinya kuo, njĩtwo mũrathime?\n" +
                "ũthamaki-inĩ wa Baba, na ndĩmwone ũthiũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nGĩkeno gĩakwa ngoro-inĩ ,nĩgũka kũiyĩrwo\n" +
                "Ndingĩtigĩra makũũmbĩ, moothe ma Jordani.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("130. Nikuri Bururi Mwega Ma");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩkũrĩ bũrũri mwega ma, na tũngĩtĩkia twawona,\n" +
                "Amu Ngai nĩatũire o kuo, atũthondekagĩra mũciĩ.\n" +
                "\n" +
                "Igũrũ kwa Jesũ, nĩtũgacemania o kuo,\n" +
                "Igũrũ kwa Baba, nĩtũgacemania o kuo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩtũkaina nyĩmbo njega, iria cia andũ atheru,\n" +
                "O na mo maroho maitũ, matikaigua ruo rĩngĩ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩtũgathage Ithe witũ, tũmũcokagĩrie mũhera,\n" +
                "Tondũ nĩ wendo wake mwene, ũtũmaga tũkenagio.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("131. Ngai Wakwa Nii Ningegaga.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNgai wakwa niĩ nĩngegaga mũno\n" +
                "Ngĩcũrania ũrĩa wee ũtariĩ\n" +
                "Ngwa na njata, mweri na indo ciothe\n" +
                "Ciagĩire nĩũndũ wa ũhoti waku.\n" +
                "\n" +
                "Ngoro yakwa rĩu nĩĩgũkũinĩra\n" +
                "Nĩ ũndũ wa ũrĩa wĩ mũnene\n" +
                "Ngoro yakwa rĩu nĩĩgũkũinĩra \n" +
                "Nĩ ũndũ wa ũrĩa wĩ mũnene.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nO kũũrĩa guothe thiaga gũkũ thĩĩ\n" +
                "Njiguaga nyoni ĩgĩkũinĩra\n" +
                "Irĩma nĩikenagia maitho mũno\n" +
                "Na karũhuho no kangenagia.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNingĩ ndaririkana Ngai Baba\n" +
                "Kĩheo gĩaku kĩa magegania\n" +
                "Jesũ Kristo akuire tũhonoke\n" +
                "Ithuothe tũkagaĩrwo muoyo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nMũkũũri Jesũ rĩrĩa agacoka\n" +
                "Anyinũkie mũciĩ wa igũrũ\n" +
                "Nĩngatũra ngumagia rĩĩtwa rĩake\n" +
                "Nangenagio nĩ wendani wake.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("132. Nii Ningwenda Ngai Umenyage.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNiĩ nĩngwenda Ngai ũmenyage nĩĩngenagio mũno nĩwe\n" +
                "Tondũ nĩũnjikaga wega na ũkanyenda hingo ciothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nIrio ciakwa iria ndĩaga,maĩ marĩa nyuaga\n" +
                "Ona nguo cia kwĩhumba,ciothe nowe ũheaga\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMuoyo naguo nĩwe waheire nĩwe ũgiragia ngue\n" +
                "ũngethengia hinya waku,ndingĩikara gathaa kamwe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nWe mũthenya ona ũtukũ,nĩũmenyagĩrĩra wega\n" +
                "Maitho maku nĩmbaraga,kũrĩ ũtheri na nduma.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nNdingĩhota gũgũcokeria,wega waku mwathani\n" +
                "No nĩndakũhoya ũtũmage,ngwende mũno na ngũiguage.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("133. Utugi Wa Magegania.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nũtugi wa magegania ,nĩ wa honokirie\n" +
                "Rĩrĩa ndarĩ mũtumumu ngĩhingũka maitho.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩ ũtugi wanyonirie,gwĩtigĩra Ngai\n" +
                "ũkĩnina guoya ngoro,rĩrĩa ndetĩkirie.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMogwati nĩ maingĩ mũno, onamo magerio\n" +
                "No ũtugi ũcio wiki,nĩ ũkanginyia gwaku.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nMwathani nĩ anjĩrĩire,irathimo nyingĩ\n" +
                "Ndĩhokaga kiugo gĩake muoyo wakwa wothe.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nNa ũtugi watũkinyia,nĩĩ tũgakenaga\n" +
                "Tũkĩgooca gatũrũme,mĩaka ngiri ngiri.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("134. Tiitheru Twi Murata.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTiitherũ twĩ mũrata,ũtahana arĩa angĩĩ\n" +
                "Jesũ nĩrĩo rĩtwa rĩake,nowe mũtũhoeri.\n" +
                "Kaĩ ũhoro ũcio naguo,nĩguo wa magegania\n" +
                "Atĩ tũngĩhoya Ngai,Ndangĩrega kũigua.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRĩu tũtĩraga nĩkĩĩ,kũmwarĩria kaingĩ ma\n" +
                "Nĩtũrĩkanĩre nake,tondũ nĩguo endaga\n" +
                "Ona tũngematha guothe,tuona ũngĩ ta ũyũ\n" +
                "Aca tũtikona ũngĩ, ũngĩtũigua ta Jesũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nO mũthenya tuona ndeto,cia mũtino kana kĩĩ\n" +
                "Tũngemakio nĩ ũrirũ, githĩ to tũmwĩre we\n" +
                "Arata aitũ arĩa ange, rĩmwe matũmenaga \n" +
                "No Jesũ ndangetĩkĩra,gũtũte nginya tene.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("135. Jesu nienda Twana Tuothe.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nJesũ nĩenda twana tuothe nĩatwendete na ngoro\n" +
                "Twana twa kũndũ guothe no ta tũmwe kwĩ Jesũ\n" +
                "Jesũ nĩenda twana tuothe tũrĩ thĩĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nJesũ nĩenda twana tuothe nĩenda gũtũrathima \n" +
                "Natuo tũngĩmwĩtĩkia we no gũtũiguĩra tha \n" +
                "Jesũ nĩenda twana tuothe tũrĩ thĩĩ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nJesũ nĩenda twana tuothe nĩagacoka gũkũ thĩĩ\n" +
                "Hĩndĩ ĩyo nĩagatũhe handũ hega igũrũ\n" +
                "Jesũ nĩenda twana tuothe tũrĩ thĩĩ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("136. Ninyenda Muno Thiage Sabbath School (NZK 209)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩnyenda mũno thiage Sabbath School, Sabbath School,\n" +
                "Nĩnyenda mũno thiage, Thabatũ rũciinĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩnyenda mũno nyinage ũhoro wa Jesũ,\n" +
                "Nĩnyenda mũno nyinage, Thabatũ rũciinĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩnyenda mũno ndutagĩre Jesũ mũhothi,\n" +
                "Nĩnyenda mũno ndutage, Thabatũ rũciinĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNĩnyenda mũno hoyage o Jesũ, O Jesũ,\n" +
                "Nĩnyenda mũno hoyage, Thabatũ rũciinĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nNĩnyenda mũno ndutagwo ũhoro wa Jesũ,\n" +
                "Nĩnyenda mũno ndutagwo, Thabatũ rũciinĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("137. Igua Mugambo Mbeca Ikigamba? (NZK 202)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nIgua Mũgambo, Mbeca ikĩgamba.\n" +
                "Ciothe nĩ cia Jesũ, Nĩaciamũkĩre.\n" +
                "\n" +
                "Nĩiragamba Nĩiragamba, Ciigue ikĩgamba,\n" +
                "Ciothe nĩ cia Jesũ, Nĩaciamũkĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNĩũgambe o kũgamba, ciume moko-inĩ,\n" +
                "Nĩ Jesũ tũrahe, Tondũ tũrĩ ake.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMũgambo ũcio wacio nĩguo kĩheo\n" +
                "Kĩrĩa tureheire Jesũ mũtwendi.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("138. Gathai Ngai Twana Tutu.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nGathai Ngai twana tũtũ tuothe,nĩ wendo nĩ wendo\n" +
                "Gathai Ngai twana tuothe tũtũ,nĩ wendo nĩ wendo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nEndai Ngai twna tũtũ tuothe nĩ wendo nĩ wendo\n" +
                "Endai Ngai twana tũtũ tuothe,nĩ wendo nĩ wendo\n" +
                ".\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nHoyai Ngai twana tũtũ tuothe,nĩ wendo nĩ wendo\n" +
                "Hoyai Ngai twana tũtũ tuothe,nĩ wendo nĩ wendo.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("139. Nianyumbiire");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNyina nyau oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina nyau oigaga Nĩanyũmbĩire, Wa gatandatũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nNyina ngui oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina ngui oigaga Nĩanyũmbĩire, Wa gatandatũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNyina ngũkũ oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina ngũkũ oigaga Nĩanyũmbĩire, Mũthi wetano.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nNyina mbata oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina mbata oigaga Nĩanyũmbĩire, Mũthi wetano.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nNyina nyoni oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina nyoni oigaga Nĩanyũmbĩire, Mũthi wetano.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t6.\nNyina ng’ombe oigaga Ngai e igũrũ, Nĩanyũmbĩire twana twakwa;\n" +
                "Nyina ng’ombe oigaga Nĩanyũmbĩire, Wa gatandatũ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("140. Zakayo");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nZakayo arĩ mũkuhĩ ma,mũndũ mũkuhĩ\n" +
                "Nĩahaicire mũtĩ nĩguo one,Mũhonokia\n" +
                "Nake Jesũ akinya ho,akĩrora mũtĩ.\n" +
                "\n" +
                "{uga kuuga ũtekũina}\n" +
                "“Akiuga Zakayo uma mũti igũrũ”\n" +
                "Nĩtũgũthiĩ nawe ogwaku\n" +
                "Nĩtũgũthiĩ nawe ogwaku");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("141. Mukunga Mbura Nĩ Wa-u? (NZK 208)");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMũkũnga mbura nĩ wa-ũ?  Nĩnjũĩ, nĩnjũĩ\n" +
                "Mũkũnga mbura nĩ wa Ngai, Nĩkĩo ndĩmwendete.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nMũkũnga mbura ũrehagwo, Nĩ Ngai, Nĩ mwega,\n" +
                "Mũkũnga mbura nĩ rũũri, Atĩ e hamwe na ithuĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("142. Kaana Ka Mariamu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nKaana ka Mariamu nĩ kega mũno,\n" +
                "Kaigĩtwo nĩ nyina, kiugũ-inĩ, he ng’ombe, ũtukũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nArĩithi makĩigua araika aingĩ,\n" +
                "Makĩinĩra Jesũ, kiugũ-inĩ, he ng’ombe, ũtukũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nAndũ ogĩ a njata maaturĩtie ndu,\n" +
                "Magĩkĩona kaana. Kiugu-inĩ, he ng’ombe, ũtukũ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nTwana tũũtũ na inyuĩ, Mwendage Jesũ,\n" +
                "ũcio waciarĩirũo kiugũ-inĩ, he ng’ombe, ũtukũ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("144. Twana Tuothe Tumenye.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTwana tuothe tũmenye\n" +
                "Twana tuothe tũmenye\n" +
                "Twana tuothe tũmenye Jesũ nũũ\n" +
                "Nĩ ihũa rĩa mũkuru\n" +
                "Nĩ njata ya rũcini\n" +
                "Nĩmwega wa ngiri ikũmi\n" +
                "ĩrai twana tuothe.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("145. Thuuragai");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nTũitho tũũtũ thuuragai wa kuona, Tũitho tũũtũ thuuragai wa kuona, Mũhonokia e igũrũ, Arorete na wendo,\n" +
                "Tũitho tũũtũ thuuragai wa kuona.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nTũtũ tũũtũ thuuragai cia kũigua, Tũtũ tũũtũ thuuragai cia kũigua, Jesũ akua atwĩrire, ũkai kũrĩ niĩ, Tũtũ tũũtũ thuuragai cia kũigua.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nTũmĩromo thuuragai cia kwaria, Tũmĩromo thuuragai cia kwaria, Tũine, Tũkĩgocaga, Tũhoe na tũkene, Tũmĩromo thuuragai cia kwaria.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nTuoko tũũtũ thuragai wa gwĩka, Tuoko tũũtũ thuragai wa gwĩka, Jesũ nĩatũrutĩire wĩra nĩtũkĩmũigue, tuoko tũtũ thuragai wa gwĩka.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t5.\nTũgũrũ thuragai gwa gũthiĩ, Tũgũrũ thuragai gwa gũthiĩ,\n" +
                "Gerai njĩra njeke, gwakia nginya gũtuke, Tũgũrũ thuragai gwa gũthiĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("146. Atutware Muciĩ");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nNĩgũũka egũũka Jesũ witũ, Atũtware mũciĩ,\n" +
                "Rĩu ngwĩhaarĩria atanoka, Gũtũtwara mũciĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nJesũ egũũka na araika, Atũtware mũciĩ,\n" +
                "Rĩu ngwĩhaarĩria atanoka, Gũtũtwara mũciĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nJesũ ekũiyĩra andũ ega, Amatware mũciĩ,\n" +
                "Rĩu ngwĩhaarĩria atanoka, Gũtũtwara mũciĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nJesũ egũũka akinyĩte itu, Atũtware mũciĩ,\n" +
                "Rĩu ngwĩhaarĩria atanoka, Gũtũtwara mũciĩ.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("147. Wamugwanja Niwa Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMĩthĩ itandatũ nĩ ya wĩra, wa mũgwanja nĩ wa Jesũ\n" +
                "ũhurũko nĩ mũtheru, tondũ ũcio nĩ wa Jesũ.\n" +
                "\n" +
                "ũmwe, ĩrĩ, ĩtatũ, ĩna, ĩtano, ĩtandatũ,\n" +
                "ĩtandatũ nĩyo iitũ, wa Mũgwanja nĩ wa Jesũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nAtwĩraga tũkahoe,mũthenya ũcio wake\n" +
                "Okire thĩ gũtũruta,tũmũrũmagĩrĩre.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nNĩtũthiĩ nyũmba-inĩ yake tũrutwo ciugo ciake\n" +
                "Rĩathani rĩake tũrũmie,nĩguo tũtũre nake.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("148. Mbuku Ikuuga.");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nMbuku ĩkuuga Ngai nĩ wendo\n" +
                "Mbuku ĩkuuga nagai nĩ wendo.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t 2.\nMbuku ĩkuuga tũtuge andũ\n" +
                "Mbuku ĩkuuga tũtuge andũ\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nMbuku ĩkuuga tũheanage\n" +
                "Mbuku ĩkuuga tũheanage.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("149. Gwitu Twaikara Na Jesu");
        song.setLyrics("\t\t\t\t\t\t\t\t\t\t1.\nGwitũ twaikara na Jesũ nĩtũkenaga\n" +
                "Nĩtũkenaga nĩtũkenaga\n" +
                "Gwitũ twaikara na Jesũ nĩtũkenaga\n" +
                "Gĩkeno kĩingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t2.\nRĩrĩa baba ena Jesũ nĩtũkenaga \n" +
                "Nĩtũkenaga nĩtũkenaga\n" +
                "Rĩrĩa baba ena Jesũ nĩtũkenaga\n" +
                "Gĩkeno kĩingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t3.\nRĩrĩa maitũ ena Jesũ nĩtũkenaga \n" +
                "Nĩtũkenaga nĩtũkenaga\n" +
                "Rĩrĩa maitũ ena Jesũ nĩtũkenaga\n" +
                "Gĩkeno kĩingĩ.\n" +
                "\n" +
                "\t\t\t\t\t\t\t\t\t\t4.\nRiria ithuothe twina Jesũ nitũkenaga\n" +
                "Nitũkenaga nitũkenaga\n" +
                "Riria ithuothe twina Jesũ nitũkenaga\n" +
                "Gĩkeno kĩingĩ");
        song.setPhoto(R.drawable.img4);
        songs.add(song);

        song=new Song();
        song.setTitle("150. Tigwoi Na Wega");
        song.setLyrics("Sabbath School nĩthiru, na nĩtwainũka\n" +
                "Sabbath School nĩthiru, na nĩtwainuka\n" +
                "Tigwoi na wega mũtuike atugi,\n" +
                "Tigwoi na wega, mũtuĩke atugi.");
        song.setPhoto(R.drawable.img4);
        songs.add(song);


        return songs;
    }

}
