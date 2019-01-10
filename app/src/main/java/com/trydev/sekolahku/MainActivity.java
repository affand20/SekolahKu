package com.trydev.sekolahku;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.trydev.sekolahku.adapter.StudentAdapter;
import com.trydev.sekolahku.database.StudentDataSource;
import com.trydev.sekolahku.database.model.Student;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private StudentDataSource dataSource;
    private ArrayList<Student> dataList;

    private ListView listViewDataSiswa;
    private StudentAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new StudentDataSource(this);
        dataList = new ArrayList<>();
        listViewDataSiswa = findViewById(R.id.list_view_siswa);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        searchView = findViewById(R.id.search_view);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                query();
            }
        });

        listViewDataSiswa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("student_id", dataList.get(position).getId());
                intent.putExtra("student_name", dataList.get(position).getNamaDepan()+" "+dataList.get(position).getNamaBelakang());
                startActivity(intent);
            }
        });

        registerForContextMenu(listViewDataSiswa);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                dataList.clear();

                dataSource.open();
                ArrayList<Student> searchResult = dataSource.searchStudent(keyword);
                dataSource.close();

                dataList.addAll(searchResult);


                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.edit){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;

            Intent intent = new Intent(MainActivity.this, FormActivity.class);
            intent.putExtra("student_id", dataList.get(index).getId());
            startActivity(intent);

        } else if (item.getItemId()==R.id.hapus){
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            int index = info.position;

            dataSource.open();
            dataSource.deleteStudent(dataList.get(index));
            dataSource.close();

            query();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        query();
    }

    private void query(){
        dataList.clear();
        dataSource.open();
        dataList = dataSource.getAllStudent();
        dataSource.close();

        adapter = new StudentAdapter(dataList, this);

        listViewDataSiswa.setAdapter(adapter);

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_data :
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
