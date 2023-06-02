package com.example.gyg.Shopping

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.gyg.R
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShoppingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

val jsonData = """
        [
            {"name": "보온백", "price": "29000", "image": "echobag", "category": "Food", "link": "https://onlyeco.co.kr/product/project1907-%EB%B3%B4%EC%98%A8%EB%B3%B4%EB%83%89%EB%B0%B1ver2/302/category/55/display/1/" },
            {"name": "필통", "price": "9000", "image": "pencilcase", "category": "Life", "link": "https://onlyeco.co.kr/product/project1907-%ED%95%84%ED%86%B5/221/category/55/display/1/" },
            {"name": "손수건", "price": "6000", "image": "handtowel", "category": "Fashion", "link": "https://onlyeco.co.kr/product/%EB%AA%A9%ED%99%94%EC%86%A1%EC%9D%B4%ED%98%91%EB%8F%99%EC%A1%B0%ED%95%A9-%EB%AC%B4%ED%98%95%EA%B4%91-%EB%A9%B4%EC%86%90%EC%88%98%EA%B1%B4/246/category/55/display/1/"},
            {"name": "클렌징 솝", "price" : "3900", "image": "clensingsoap", "category": "Beauty", "link": "https://onlyeco.co.kr/product/%ED%95%84%EB%A7%81%EB%B9%88-%EB%B0%94%EB%A6%AC%EC%8A%A4%ED%83%80%EC%8A%A4-%ED%81%B4%EB%A0%8C%EC%A7%95%EC%86%9D%EC%84%B8%EC%95%88%EB%B9%84%EB%88%84/465/category/56/display/1/"}
        ]
    """.trimIndent()

class ShoppingFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private fun lookUpProduct(query: String, gridLayout:GridLayout) {
        gridLayout.removeAllViews()
        val jsonArray = JSONArray(jsonData)


        val context = requireContext()
        for (i in 0 until jsonArray.length()) {

            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val name: String = jsonObject.getString("name")
            val price: String = jsonObject.getString("price")
            val image: String = jsonObject.getString("image")
            val link: String = jsonObject.getString("link")
            val category: String = jsonObject.getString("category")

            // 카테고리 검색을 위한 쿼리
            if (query != "All" && query != category) {
                continue
            }

            val cardView = CardView(context)
            val layoutParams = GridLayout.LayoutParams()

            // CardView의 크기 설정
            val sizeInDp = 50f
            val sizeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDp, resources.displayMetrics)
            layoutParams.width = sizeInPx.toInt()
            layoutParams.height = sizeInPx.toInt()

            // CardView의 위치와 가중치 설정
            layoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)

            // CardView의 마진 설정
            val marginInDp = 5f
            val marginInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginInDp, resources.displayMetrics)
            layoutParams.setMargins(marginInPx.toInt(), marginInPx.toInt(), marginInPx.toInt(), marginInPx.toInt())

            // CardView의 모서리 둥글기 설정
            val cornerRadiusInDp = 20f
            val cornerRadiusInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInDp, resources.displayMetrics)
            cardView.radius = cornerRadiusInPx

            // CarView에 이미지뷰 추가
            val imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            if (image == "pencilcase") {
                imageView.setImageResource(R.drawable.pencilcase)
            } else if ( image == "handtowel" ) {
                imageView.setImageResource(R.drawable.handtowel)
            } else if ( image == "echobag" ) {
                imageView.setImageResource(R.drawable.echobag)
            } else {
                imageView.setImageResource(R.drawable.clensingsoap)
            }

            imageView.scaleType = ImageView.ScaleType.FIT_CENTER

            val linearLayout = LinearLayout(context)
            val linearParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.gravity = Gravity.BOTTOM

            // LinearLayout의 마진 설정
            val marginLinear = 7f
            val marginLinearPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginLinear, resources.displayMetrics)
            linearParams.setMargins(marginLinearPx.toInt(), marginLinearPx.toInt(), marginLinearPx.toInt(), marginLinearPx.toInt())
            linearLayout.layoutParams = linearParams

            // 첫 번째 TextView 추가
            val textView1 = TextView(context)
            textView1.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView1.text = "자연 상점"
            textView1.setTextColor(ContextCompat.getColor(context, androidx.leanback.R.color.lb_default_search_color))
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)

            // 두 번째 TextView 추가
            val textView2 = TextView(context)
            textView2.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView2.text = name
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)

            // 세 번째 TextView 추가
            val textView3 = TextView(context)
            textView3.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView3.text = price + "원"
            textView3.setTypeface(null, Typeface.BOLD)
            textView3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)

            cardView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(link)
                startActivity(intent)
            }

            linearLayout.addView(textView1)
            linearLayout.addView(textView2)
            linearLayout.addView(textView3)

            // CardView의 하위 요소 추가
            cardView.addView(imageView)
            cardView.addView(linearLayout)

            // CardView를 GridLayout에 추가
            gridLayout.addView(cardView, layoutParams)
        }
    }

    private fun productSearch(query: String, gridLayout: GridLayout) {
        gridLayout.removeAllViews()
        val jsonArray = JSONArray(jsonData)


        val context = requireContext()
        for (i in 0 until jsonArray.length()) {

            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val name: String = jsonObject.getString("name")
            val price: String = jsonObject.getString("price")
            val image: String = jsonObject.getString("image")
            val link: String = jsonObject.getString("link")
            val category: String = jsonObject.getString("category")

            // 검색어 검색을 위한 쿼리
            if (query !in name) {
                continue
            }

            val cardView = CardView(context)
            val layoutParams = GridLayout.LayoutParams()

            // CardView의 크기 설정
            val sizeInDp = 50f
            val sizeInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDp, resources.displayMetrics)
            layoutParams.width = sizeInPx.toInt()
            layoutParams.height = sizeInPx.toInt()

            // CardView의 위치와 가중치 설정
            layoutParams.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            layoutParams.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)

            // CardView의 마진 설정
            val marginInDp = 5f
            val marginInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginInDp, resources.displayMetrics)
            layoutParams.setMargins(marginInPx.toInt(), marginInPx.toInt(), marginInPx.toInt(), marginInPx.toInt())

            // CardView의 모서리 둥글기 설정
            val cornerRadiusInDp = 20f
            val cornerRadiusInPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, cornerRadiusInDp, resources.displayMetrics)
            cardView.radius = cornerRadiusInPx

            // CarView에 이미지뷰 추가
            val imageView = ImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            if (image == "pencilcase") {
                imageView.setImageResource(R.drawable.pencilcase)
            } else if ( image == "handtowel" ) {
                imageView.setImageResource(R.drawable.handtowel)
            } else if ( image == "echobag" ) {
                imageView.setImageResource(R.drawable.echobag)
            } else {
                imageView.setImageResource(R.drawable.clensingsoap)
            }

            imageView.scaleType = ImageView.ScaleType.FIT_CENTER

            val linearLayout = LinearLayout(context)
            val linearParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.gravity = Gravity.BOTTOM

            // LinearLayout의 마진 설정
            val marginLinear = 7f
            val marginLinearPx = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginLinear, resources.displayMetrics)
            linearParams.setMargins(marginLinearPx.toInt(), marginLinearPx.toInt(), marginLinearPx.toInt(), marginLinearPx.toInt())
            linearLayout.layoutParams = linearParams

            // 첫 번째 TextView 추가
            val textView1 = TextView(context)
            textView1.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView1.text = "자연 상점"
            textView1.setTextColor(ContextCompat.getColor(context, androidx.leanback.R.color.lb_default_search_color))
            textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)

            // 두 번째 TextView 추가
            val textView2 = TextView(context)
            textView2.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView2.text = name
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)

            // 세 번째 TextView 추가
            val textView3 = TextView(context)
            textView3.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            textView3.text = price + "원"
            textView3.setTypeface(null, Typeface.BOLD)
            textView3.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20f)

            cardView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(link)
                startActivity(intent)
            }

            linearLayout.addView(textView1)
            linearLayout.addView(textView2)
            linearLayout.addView(textView3)

            // CardView의 하위 요소 추가
            cardView.addView(imageView)
            cardView.addView(linearLayout)

            // CardView를 GridLayout에 추가
            gridLayout.addView(cardView, layoutParams)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shopping , container , false)

        // 그리드 레이아웃에 상품을 초기화 해주기 위해 요소를 가져옴
        val gridLayout:GridLayout = view.findViewById(R.id.itemView)

        lookUpProduct("All", gridLayout)

        // 각 카테고리 쿼리 구현
        val categoryAll:CardView = view.findViewById(R.id.categoryAll)
        categoryAll.setOnClickListener {
            lookUpProduct("All", gridLayout)
        }

        val categoryFashion:CardView = view.findViewById(R.id.categoryFashion);
        categoryFashion.setOnClickListener {
            lookUpProduct("Fashion", gridLayout)
        }

        val categoryBeauty:CardView = view.findViewById(R.id.categoryBeauty);
        categoryBeauty.setOnClickListener {
            lookUpProduct("Beauty", gridLayout)
        }

        val categoryFood:CardView = view.findViewById(R.id.categoryFood);
        categoryFood.setOnClickListener {
            lookUpProduct("Food", gridLayout)
        }

        val categoryLife:CardView = view.findViewById(R.id.categoryLife);
        categoryLife.setOnClickListener {
            lookUpProduct("Life", gridLayout)
        }

        val categoryPet:CardView = view.findViewById(R.id.categoryPet);
        categoryPet.setOnClickListener {
            lookUpProduct("Pet", gridLayout)
        }

        // searchView를 이용하여 검색하는 기능
        val searchView:androidx.appcompat.widget.SearchView = view.findViewById(R.id.productSearch)
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                productSearch(query, gridLayout)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                productSearch(newText, gridLayout)
                return true
            }

        })

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShoppingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String , param2: String) =
            ShoppingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1 , param1)
                    putString(ARG_PARAM2 , param2)
                }
            }
    }
}