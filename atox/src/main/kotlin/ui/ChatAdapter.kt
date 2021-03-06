package ltd.evilcorp.atox.ui

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import ltd.evilcorp.atox.R
import ltd.evilcorp.core.vo.Message
import ltd.evilcorp.core.vo.Sender
import java.text.DateFormat

private fun inflateView(type: ChatItemType, inflater: LayoutInflater): View =
    inflater.inflate(
        when (type) {
            ChatItemType.SentMessage -> R.layout.message_sent
            ChatItemType.ReceivedMessage -> R.layout.message_received
        },
        null,
        true
    )

private enum class ChatItemType {
    ReceivedMessage,
    SentMessage
}

private val types = ChatItemType.values()
private val timeFormatter = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT)

private class MessageViewHolder(row: View) {
    val message = row.findViewById(R.id.message) as TextView
    val timestamp = row.findViewById(R.id.timestamp) as TextView
}

class ChatAdapter(
    private val inflater: LayoutInflater,
    private val resources: Resources
) : BaseAdapter() {
    var messages: List<Message> = ArrayList()

    override fun getCount(): Int = messages.size
    override fun getItem(position: Int): Any = messages[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getViewTypeCount(): Int = types.size
    override fun getItemViewType(position: Int): Int = when (messages[position].sender) {
        Sender.Sent -> ChatItemType.SentMessage.ordinal
        Sender.Received -> ChatItemType.ReceivedMessage.ordinal
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View =
        when (val type = types[getItemViewType(position)]) {
            ChatItemType.ReceivedMessage, ChatItemType.SentMessage -> {
                val message = messages[position]
                val view: View
                val vh: MessageViewHolder

                if (convertView != null) {
                    view = convertView
                    vh = view.tag as MessageViewHolder
                } else {
                    view = inflateView(type, inflater)
                    vh = MessageViewHolder(view)
                    view.tag = vh
                }

                vh.message.text = message.message
                vh.timestamp.text = if (message.timestamp != 0L) {
                    timeFormatter.format(message.timestamp)
                } else {
                    resources.getText(R.string.sending)
                }

                view
            }
        }
}
