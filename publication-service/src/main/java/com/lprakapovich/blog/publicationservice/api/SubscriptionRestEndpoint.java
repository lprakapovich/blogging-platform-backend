package com.lprakapovich.blog.publicationservice.api;

import com.lprakapovich.blog.publicationservice.api.dto.SubscriberDto;
import com.lprakapovich.blog.publicationservice.api.dto.SubscriptionDto;
import com.lprakapovich.blog.publicationservice.model.Subscription;
import com.lprakapovich.blog.publicationservice.service.BlogService;
import com.lprakapovich.blog.publicationservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static com.lprakapovich.blog.publicationservice.util.BlogIdResolver.resolveBlogIdFromPrincipal;

@RestController
@RequestMapping("/subscriptions")
@RequiredArgsConstructor
class SubscriptionRestEndpoint {

    private final SubscriptionService subscriptionService;
    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<URI> addSubscription(@Valid @RequestBody SubscriptionDto subscriptionDto) {
        String subscriberId = resolveBlogId();
        String subscriptionTargetId = subscriptionDto.getSubscriptionTargetBlogId();
        blogService.validateExistence(subscriptionTargetId);
        Subscription subscription = new Subscription(subscriptionTargetId, subscriberId);
        Subscription.SubscriptionId subscriptionId = subscriptionService.saveSubscription(subscription);
        URI uri = URI.create(String.join(",", subscriptionId.getBlogId(), subscriptionId.getSubscriberBlogId()));
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable(value = "id") String blogIdToUnsubscribe) {
        String subscriberId = resolveBlogId();
        subscriptionService.deleteSubscription(new Subscription(blogIdToUnsubscribe, subscriberId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<SubscriptionDto>> getAllSubscriptions() {
        String blogId = resolveBlogId();
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions(blogId);
        List<SubscriptionDto> subscriberDtos = subscriptions
                .stream()
                .map(subscription -> new SubscriptionDto(subscription.getId().getBlogId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(subscriberDtos);
    }

    @GetMapping("/subscribers")
    public ResponseEntity<List<SubscriberDto>> getAllSubscribers() {
        String blogId = resolveBlogId();
        List<Subscription> subscriptions = subscriptionService.getAllSubscribers(blogId);
        List<SubscriberDto> subscriberDtos = subscriptions.stream()
                .map(subscription -> new SubscriberDto(subscription.getId().getSubscriberBlogId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(subscriberDtos);
    }

    // TODO instead of resolving blogId each time, add interceptor to compare values from path and token
    private String resolveBlogId() {
        String blogId = resolveBlogIdFromPrincipal();
        blogService.validateExistence(blogId);
        return blogId;
    }
}
