package com.blabla.dontruinyourlaundry.adapters

import android.app.AlertDialog
import android.app.ProgressDialog.show
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.blabla.dontruinyourlaundry.R
import com.blabla.dontruinyourlaundry.data.SymbolForWashing
import com.blabla.dontruinyourlaundry.entity.TypeOfRecyclerView

class RecyclerViewAdapterSymbolInGrid(
    private var data: List<SymbolForWashing>,
    private val type: TypeOfRecyclerView
) :
    RecyclerView.Adapter<RecyclerViewAdapterSymbolInGrid.ItemViewHolder>() {



    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val symbol: ImageButton = view.findViewById(R.id.symbol_in_guide)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val adapterLayout =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.symbol_guide_symbols_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {

        val symbolItem= data[position]
        holder.symbol.setImageResource(symbolItem.pictureId)

        when (type) {
            TypeOfRecyclerView.SYMBOLGUIDEFRAGMENT -> holder.symbol.setOnClickListener {
                val builder = AlertDialog.Builder(holder.symbol.context, R.style.AlertDialogTheme).apply {
                    setMessage(symbolItem.meaningOfSymbol)
                    setPositiveButton("Ok") { _, _ -> }
                    show()

                    //dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLUE)
                    //val builder: AlertDialog.Builder = AlertDialog.Builder(this)
                    //val dialog: AlertDialog = builder.setTitle("SomeText")
                    //           .setMessage("SomeMessage")
                    //           .setPositiveButton("OK") {
                    //               dialog, which -> dialog.dismiss()
                    //           }
                    //           .setNegativeButton("Cancel") { dialog, which -> dialog.dismiss()
                    //
                    //           }
                    //           .create()
                    //dialog.show()
                }
            }
            TypeOfRecyclerView.ADDSYMBOLFRAGMENT -> {
                var bgColor: Int
                holder.symbol.setOnClickListener {
                    bgColor = if (symbolItem.selected) {
                        R.color.lilac_200
                    } else {
                        R.color.lilac_700

                    }
                    holder.symbol.setBackgroundColor(
                        holder.symbol.context.resources.getColor(
                            bgColor
                        )
                    )
                    //change status of symbol
                    symbolItem.selected = !symbolItem.selected
                }

            }
        }

    }

    override fun getItemCount() = data.size
}