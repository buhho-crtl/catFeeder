package adriandp.feedercat.view.viewmodel.item

import adriandp.feedercat.R
import adriandp.feedercat.listener.ListenerDialogTime
import adriandp.feedercat.model.Config
import adriandp.feedercat.model.Data
import adriandp.feedercat.view.util.DialogTimePick
import adriandp.feedercat.view.viewmodel.MainActivityVM
import android.content.Context
import androidx.databinding.BaseObservable
import java.text.SimpleDateFormat
import java.util.*


class ItemDataVM(
    val config: Config? = null,
    val data: Data? = null
) : BaseObservable(), ListenerDialogTime {

    private var mainPresenter: MainActivityVM? = null

    fun textHour(context: Context): String {
        return if (config != null) {
            context.getString(R.string.hour, config.hour, config.minutes)
        } else {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            dateFormat.format(Date(data!!.time.toLong() * 1000))
        }
    }


    fun onClickCard(context: Context, presenter: MainActivityVM, config: Config) {
        this.mainPresenter = presenter
        DialogTimePick(context, this, config)
    }

    override fun acceptDialog(auxConfig: Config) {
        if (this.config!!.hour != auxConfig.hour ||
            this.config.minutes != auxConfig.minutes ||
            this.config.feed != auxConfig.feed ||
            this.config.enable != auxConfig.enable
        ) {
            mainPresenter?.updateConfig(config.apply {
                this.hour = auxConfig.hour
                this.minutes = auxConfig.minutes
                this.feed = auxConfig.feed
                this.enable = auxConfig.enable
            })
        }
    }

}