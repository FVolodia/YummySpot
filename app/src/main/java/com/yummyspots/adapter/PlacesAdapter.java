package com.yummyspots.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yummyspots.R;
import com.yummyspots.foursquare.FoursquareService;
import com.yummyspots.foursquare.model.VenuesDetail;
import com.yummyspots.model.Badge;
import com.yummyspots.model.Place;
import com.yummyspots.util.SimpleCache;
import com.yummyspots.view.BadgeView;
import com.github.johnkil.print.PrintView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by FVolodia on 14.09.15.
 */
public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PersonViewHolder> {

    private List<Place> items;
    private OnPlaceClickListener mOnItemClickListener;
    private LayoutInflater inflater;
    private FoursquareService fsqService;
    private Callback<VenuesDetail> callback;

    public interface OnPlaceClickListener {
        void onPlaceClick(View view, int position, Place place);

        void onLikeClick(View view, Place place);
    }

    public void setItems(List<Place> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public PlacesAdapter(Context context) {
        this(new ArrayList<Place>(), context);
    }

    public PlacesAdapter(List<Place> items, Context context) {
        this.items = items;
        inflater = LayoutInflater.from(context);
        fsqService = new FoursquareService(context);
    }

    public PlacesAdapter withPlaceClickListener(OnPlaceClickListener listener) {
        this.mOnItemClickListener = listener;
        return this;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.item_place, parent, false);
        final PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, final int position) {
        final Place currentPlace = items.get(position);
        final String curentTitle = currentPlace.getTitle();
        final String curentLocation = currentPlace.getLocation();


        Context context = holder.placeTitle.getContext();

        holder.placeTitle.setText(curentTitle);
        holder.placeLocations.setText(curentLocation);
        holder.placeRating.setText(currentPlace.getRating());
        holder.placeLike.setText(currentPlace.getRatingSignals());

        VenuesDetail cached = (VenuesDetail) SimpleCache.get(currentPlace.getFoursquareID());
        if (cached != null) {
            fillVenueDetails(cached, holder, currentPlace);
        } else {
            holder.placePicture.setImageResource(R.drawable.temp_rest);
            callback = new Callback<VenuesDetail>() {
                @Override
                public void onResponse(Response<VenuesDetail> response, Retrofit retrofit) {
                    SimpleCache.cache(currentPlace.getFoursquareID(), response.body());
                    fillVenueDetails(response.body(), holder, currentPlace);
                }

                @Override
                public void onFailure(Throwable t) {

                }
            };
            fsqService.getVenueDetails(callback, currentPlace.getFoursquareID());
        }

        holder.cardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onPlaceClick(v, position, currentPlace);
                }
            }
        });

        Collection<Badge> allBadge = Badge.badgeList();
        holder.llBadgeOnPlace.removeAllViews();
        for (Integer badgeId : currentPlace.getBadges()) {
            Badge b = Badge.badgeMap().get(badgeId);
            createBadge(b, holder.llBadgeOnPlace);
        }

        int[] colors = context.getResources().getIntArray(R.array.categoryColors);
        Random r = new Random();
        r.setSeed(currentPlace.getFoursquareID().hashCode());
        int colorIndex = r.nextInt(colors.length);
        holder.infoContainer.setBackgroundColor(colors[colorIndex]);

    }

    private void fillVenueDetails(VenuesDetail venuesDetail, PersonViewHolder holder, Place currentPlace) {
        try {
            Picasso.with(holder.placeTitle.getContext()).load(venuesDetail.getBestPhoto().getUrlPhoto()).into(holder.placePicture);
            currentPlace.setImagePlaceUrl(venuesDetail.getBestPhoto().getUrlPhoto());
        } catch (Exception e) {
            holder.placePicture.setImageResource(R.drawable.temp_rest);
        }
        currentPlace.setRating(venuesDetail.getRating());
        if (!TextUtils.isEmpty(currentPlace.getRating())) {
            holder.placeRating.setText(currentPlace.getRating());
            holder.ratingStar.setIconTextRes(R.string.ic_material_star);
        } else {
            holder.placeRating.setText("N/A");
            holder.ratingStar.setIconTextRes(R.string.ic_material_star_outline);
        }

        currentPlace.setRatingSignals(venuesDetail.getRatingSignals());
        if (!TextUtils.isEmpty(currentPlace.getRatingSignals())) {
            holder.placeLike.setText(currentPlace.getRatingSignals());
            holder.likeHeart.setIconTextRes(R.string.ic_material_favorite);
        } else {
            holder.likeHeart.setIconTextRes(R.string.ic_material_favorite_outline);
            holder.placeLike.setText("");
        }

        currentPlace.setCategory(venuesDetail.getCategories().get(0).getName());
    }

    private BadgeView createBadge(Badge b, ViewGroup viewGroup) {
        BadgeView result = (BadgeView) inflater.inflate(R.layout.view_badge_on_place, viewGroup, false);
        result.setIconCodeRes(b.getCodeName());
        result.setHightLightColor(b.getColor());
        result.setSelected(true);
        result.setClickable(false);
        viewGroup.addView(result);
        return result;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.place_title)
        TextView placeTitle;

        @Bind(R.id.place_locations)
        TextView placeLocations;

        @Bind(R.id.imgPlacePicture)
        ImageView placePicture;

        @Bind(R.id.cardContainer)
        View cardContainer;

        @Bind(R.id.llBadgeOnPlace)
        LinearLayout llBadgeOnPlace;

        @Bind(R.id.tvRating)
        TextView placeRating;

        @Bind(R.id.tvLike)
        TextView placeLike;

        @Bind(R.id.infoContainer)
        View infoContainer;

        @Bind(R.id.pvStar)
        PrintView ratingStar;

        @Bind(R.id.pvHeart)
        PrintView likeHeart;

        public PersonViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
