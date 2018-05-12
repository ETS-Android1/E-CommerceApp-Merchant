package com.rowland.delivery.features.dash.presentation.fragments;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rowland.delivery.features.dash.di.modules.order.OrderModule;
import com.rowland.delivery.features.dash.presentation.activities.DashActivity;
import com.rowland.delivery.features.dash.presentation.adapters.OrderDataAdapter;
import com.rowland.delivery.features.dash.presentation.tools.recylerview.RecyclerItemClickListener;
import com.rowland.delivery.features.dash.presentation.viewmodels.order.OrderViewModel;
import com.rowland.delivery.merchant.R;
import com.rowland.delivery.utilities.DumyDataUtility;
import com.vlonjatg.progressactivity.ProgressFrameLayout;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Rowland on 5/7/2018.
 */
public class OrderFragment extends Fragment {

    private Unbinder unbinder;
    private OrderViewModel orderViewModel;

    @BindView(R.id.orders_recyclerview)
    RecyclerView orderRecyclerview;
    @BindView(R.id.progressActivity)
    ProgressFrameLayout progressActivity;

    @Inject
    @Named("order")
    ViewModelProvider.Factory orderFactory;
    @Inject
    OrderDataAdapter orderAdapter;

    public OrderFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderViewModel = ViewModelProviders.of(getActivity(), orderFactory).get(OrderViewModel.class);
        orderViewModel.setFirebaseUserUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((DashActivity) getActivity()).getDashComponent()
                .getOrderComponent(new OrderModule())
                .inject(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        orderRecyclerview.setLayoutManager(linearLayoutManager);
        orderRecyclerview.setItemAnimator(new DefaultItemAnimator());
        orderRecyclerview.setAdapter(orderAdapter);

        orderRecyclerview.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), orderRecyclerview, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                orderViewModel.select(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {}
        }));

        orderViewModel.getOrderDataList()
                .observe(this, orders -> orderAdapter.setList(orders));

       /*FirebaseFirestore.getInstance()
                .collection("orderdata")
                .add(DumyDataUtility.createOrderData()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getActivity(), "OrderData saved", Toast.LENGTH_SHORT)
                        .show();
            }
        });
*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unbinder.unbind();
    }
}
